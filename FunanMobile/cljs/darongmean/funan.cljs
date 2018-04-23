(ns darongmean.funan
  (:require
    [react-native.core :as rn]
    [rum.core :as rum]

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
  (.then (.getImageSource Icon "home" 30) start-app))
