(ns darongmean.funan
  (:require
    [react-native.core :as rn]
    [rum.core :as rum]
    [cljs.pprint :as pprint]
    [citrus.core :as citrus]
    ["react-native-navigation" :refer [Navigation]]
    ["react-native-vector-icons/Octicons" :as Icon]))


(enable-console-print!)


(rum/defc hello-world []
  (rn/text {:style {:font-size   30
                    :font-weight :bold}}
           "Hello, world!"))


(def state-chart-transitions
  {'Initial           {:RUN-FOREGROUND 'Loading}
   'Loading           {:SHOW-SCREEN 'ListingFeedScreen}
   'ListingFeedScreen {}})


(defmulti activity-on-enter (fn [app-state event-params] (get-in app-state [:state :state-id])))


(defmethod activity-on-enter 'Loading [app-state]
  (-> app-state
      (assoc :do-register-component [])
      (assoc :do-load-icon [])))


(defmethod activity-on-enter 'ListingFeedScreen [app-state [icon]]
  (let [updated (assoc-in app-state [:state :icon] icon)]
    (assoc updated :do-show-screen (:state updated))))


(defmulti transition-on-event (fn [event-signal] event-signal))


(defmethod transition-on-event :init []
  {:state {:state-id 'Initial :icon {}}})


(defmethod transition-on-event :default
  [event-signal event-params {:keys [state-id] :as state-data}]
  (let [next-state-id (get-in state-chart-transitions [state-id event-signal])
        next-state {:state (assoc state-data :state-id next-state-id)}]
    (if next-state-id
      (activity-on-enter next-state event-params)
      {:state state-data})))


(defn do-load-icon [rr ctrl _]
  (-> Icon
      (.getImageSource "home" 30)
      (.then #(citrus/dispatch! rr ctrl :SHOW-SCREEN {"home" %1}))))


(defn do-register-component [_ _ _]
  (doto Navigation
    (.registerComponent "example.FirstScreen" (fn [] (:rum/class (meta hello-world))))))


(defn do-show-screen [_ _ state]
  (let [icon (get-in state [:icon "home"])]
    (doto Navigation
      (.startTabBasedApp (clj->js {:tabs [{:screen "example.FirstScreen"
                                           :title  "Home"
                                           :label  "Home"
                                           :icon   icon}]})))))


(def reconciler
  (citrus/reconciler
    {:state
     (atom {})
     :controllers
     {:state transition-on-event}
     :effect-handlers
     {:do-register-component do-register-component
      :do-load-icon          do-load-icon
      :do-show-screen        do-show-screen}}))


;(defonce init-ctrl (citrus/broadcast-sync! reconciler :init))

(def debug
  (add-watch
    reconciler
    :debug
    #(do
       (println "---debug state---")
       (pprint/pprint %4)
       (println "---debug state---"))))


(defn main []
  (citrus/broadcast-sync! reconciler :init)
  (citrus/broadcast! reconciler :RUN-FOREGROUND))
