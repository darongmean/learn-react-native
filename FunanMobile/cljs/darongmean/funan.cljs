(ns darongmean.funan
  (:require
    [react-native.core :as rn]
    [rum.core :as rum]

    ["react-native-navigation" :refer [Navigation]]))


(rum/defc hello-world []
  (rn/text {:style {:font-size   30
                    :font-weight :bold}}
           "Hello, world!"))


(defn main []
  (.registerComponent Navigation "example.FirstScreen" (fn [] (:rum/class (meta hello-world))))
  (.startSingleScreenApp Navigation (clj->js {:screen {:screen "example.FirstScreen" :title "Welcome"}})))
