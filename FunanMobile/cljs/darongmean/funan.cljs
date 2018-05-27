(ns darongmean.funan
  (:require-macros
    [cljs.core.async.macros :as async-cljs])
  (:require
    [react-native.core :as rn]
    [rum.core :as rum]
    [cljs.core.async :as async]
    ["react-native-navigation" :refer [Navigation]]
    ["react-native-vector-icons/Octicons" :as Icon]))


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


(def state (atom {:id 'Initial}))


(def action (async/chan))


(defn load-resource []
  (do
    (doto Navigation
      (.registerComponent "example.FirstScreen" (fn [] (:rum/class (meta hello-world)))))
    (-> Icon
        (.getImageSource "home" 30)
        (.then #(do
                  (swap! state assoc-in [:icon :home] %1)
                  (async/put! action :SHOW-SCREEN))))))


(defn show-listing-feed-screen []
  (start-app (get-in @state [:icon :home])))


(defn update-state [{:keys [id] :as prev-state} signal]
  (let [next-state (get-in state-chart-machine [id signal])]
    (condp = next-state
      'Loading (load-resource)
      'ListingFeedScreen (show-listing-feed-screen))
    (-> prev-state
        (assoc :id next-state))))


(defn icon-chan [name size]
  (let [ch (async/chan)]
    (-> Icon (.getImageSource name size) (.then #(async/put! ch %1)))
    ch))


(defn main []
  (async-cljs/go
    (loop []
      (let [next-state (update-state @state (async/<! action))]
        (reset! state next-state)
        (recur))))
  (async/put! action :RUN-FOREGROUND))
