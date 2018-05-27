(ns darongmean.funan
  (:require-macros
    [cljs.core.async.macros :as async-cljs])
  (:require
    [react-native.core :as rn]
    [rum.core :as rum]
    [cljs.core.async :as async]
    ["react-native-navigation" :refer [Navigation]]
    ["react-native-vector-icons/Octicons" :as Icon]
    [darongmean.state-machine :as machine]))


(rum/defc hello-world []
  (rn/text {:style {:font-size   30
                    :font-weight :bold}}
           "Hello, world!"))


(defn start-app [icon]
  (doto Navigation
    (.startTabBasedApp (clj->js {:tabs [{:screen "example.FirstScreen"
                                         :title  "Home"
                                         :label  "Home"
                                         :icon   icon}]}))))


(def state-chart-machine
  {'Initial           {:RUN-FOREGROUND 'Loading}
   'Loading           {:SHOW-SCREEN 'ListingFeedScreen}
   'ListingFeedScreen {}})


(def app-context (atom {:state 'Initial :icon {}}))


(def action (async/chan))


(defn load-resource [context]
  (do
    (doto Navigation
      (.registerComponent "example.FirstScreen" (fn [] (:rum/class (meta hello-world)))))
    (-> Icon
        (.getImageSource "home" 30)
        (.then #(async/put! action [:SHOW-SCREEN %1]))))
  context)


(defn show-listing-feed-screen [context]
  (start-app (get-in context [:icon :home])))


(defn next-state [{:keys [state] :as context} signal]
  (let [next-state (get-in state-chart-machine [state signal])]
    (-> context
        (assoc :state next-state))))


(defn icon-chan [name size]
  (let [ch (async/chan)]
    (-> Icon (.getImageSource name size) (.then #(async/put! ch %1)))
    ch))


(defmethod machine/apply-action-on-enter 'Loading [context _]
  (load-resource context))


(defmethod machine/apply-action-on-enter 'ListingFeedScreen [context params]
  (assoc-in context [:icon :home] params))



(defmethod machine/render 'Loading [_])


(defmethod machine/render 'ListingFeedScreen [context]
  (show-listing-feed-screen context))


(defn main []
  (async-cljs/go
    (loop [event :RUN-FOREGROUND]
      (let [[signal params] (if (coll? event) event [event])
            context (next-state @app-context signal)
            context (machine/apply-action-on-enter context params)
            _ (machine/render context)
            _ (reset! app-context context)]
        (recur (async/<! action))))))
