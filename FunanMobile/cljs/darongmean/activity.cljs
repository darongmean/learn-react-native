(ns darongmean.activity
  (:require
    [react-native.core :as rn]
    [rum.core :as rum]
    [citrus.core :as citrus]
    ["react-native-navigation" :refer [Navigation]]
    ["react-native-vector-icons/Octicons" :as Icon]))


(rum/defc hello-world []
  (rn/text {:style {:font-size   30
                    :font-weight :bold}}
           "Hello, world!"))


(defn do-load-icon [rr _ _]
  (-> Icon
      (.getImageSource "home" 30)
      (.then #(citrus/broadcast! rr :icon-loaded :home %1))))


(defn do-register-component [rr _ _]
  (doto Navigation
    (.registerComponent "example.FirstScreen" (fn [] (:rum/class (meta hello-world)))))
  (citrus/broadcast! rr :component-registered))


(defn do-show-screen [_ _ context]
  (let [icon (get-in context [:icon :home])]
    (doto Navigation
      (.startTabBasedApp (clj->js {:tabs [{:screen "example.FirstScreen"
                                           :title  "Home"
                                           :label  "Home"
                                           :icon   icon}]})))))
