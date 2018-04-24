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
    (.registerComponent "example.FirstScreen" (fn [] (:rum/class (meta hello-world))))
    (.startTabBasedApp (clj->js {:tabs [{:screen "example.FirstScreen"
                                         :title  "Home"
                                         :label  "Home"
                                         :icon   icon}]}))))


(defn icon-chan [name size]
  (let [ch (async/chan)]
    (-> Icon (.getImageSource name size) (.then #(async/put! ch %1)))
    ch))


(defn main []
  (let [home-icon (icon-chan "home" 30)]
    (async-cljs/go (start-app (async/<! home-icon)))))
