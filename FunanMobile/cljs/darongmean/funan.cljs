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
  {'Initial           {:on {:RUN-FOREGROUND 'Loading}}
   'Loading           {:on       {:SHOW-SCREEN 'ListingFeedScreen}
                       :on-enter [:do-register-component :do-load-icon]}
   'ListingFeedScreen {:on-enter [:do-show-screen]}})


(defmulti context-on-enter (fn [context event-params] (:state-id context)))


(defmethod context-on-enter :default [m _] m)


(defmethod context-on-enter 'ListingFeedScreen [context [icon]]
  (assoc context :icon icon))


(defn activity-on-enter [app-state]
  (let [{:keys [state-id state-chart] :as context} (:state app-state)
        activity-id-coll (get-in state-chart [state-id :on-enter])
        activity (->> (repeat context)
                      (interleave activity-id-coll)
                      (apply hash-map))]
    (merge app-state activity)))


(defmulti transition-on-event (fn [event-signal] event-signal))


(defmethod transition-on-event :init []
  {:state {:state-id 'Initial :state-chart state-chart-transitions}})


(defmethod transition-on-event :default
  [event-signal event-params {:keys [state-id state-chart] :as context}]
  (let [next-state-id (get-in state-chart [state-id :on event-signal])
        next-context (assoc context :state-id next-state-id)
        next-context (context-on-enter next-context event-params)
        next-state {:state next-context}]
    (if next-state-id
      (activity-on-enter next-state)
      {:state context})))


(defn do-load-icon [rr _ _]
  (-> Icon
      (.getImageSource "home" 30)
      (.then #(citrus/broadcast! rr :SHOW-SCREEN {"home" %1}))))


(defn do-register-component [_ _ _]
  (doto Navigation
    (.registerComponent "example.FirstScreen" (fn [] (:rum/class (meta hello-world))))))


(defn do-show-screen [_ _ context]
  (let [icon (get-in context [:icon "home"])]
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
     {:context transition-on-event}
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
