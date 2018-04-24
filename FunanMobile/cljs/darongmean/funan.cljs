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
  (.registerComponent Navigation "example.FirstScreen" (fn [] (:rum/class (meta hello-world))))
  (.startTabBasedApp Navigation (clj->js {:tabs [{:screen "example.FirstScreen" :title "Home" :label "Home" :icon icon}]})))


(defn main []
  (let [ch (async/chan)]
    (.then (.getImageSource Icon "home" 30) #(async/put! ch %1))
    (async-cljs/go
      (start-app (async/<! ch)))))
