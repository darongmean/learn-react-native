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


(def rum-by-keyword
  {:hello-world hello-world})


(defn do-load-icon [rr _ xs]
  (doseq [kw xs]
    (-> Icon
        (.getImageSource (name kw) 30)
        (.then #(citrus/broadcast! rr :icon-loaded kw %1)))))


(defn do-register-component [rr _ coll]
  (doseq [[kw comp-name] (seq coll)
          :let [comp-rum (rum-by-keyword kw)]]
    (.registerComponent Navigation comp-name (fn [] (:rum/class (meta comp-rum)))))
  (citrus/broadcast! rr :component-registered coll))


(defn do-show-screen [_ _ context]
  (let [screen-key (:mode/screen context)
        screen-name (get-in context [:screen screen-key])
        icon (get-in context [:icon :home])]
    (doto Navigation
      (.startTabBasedApp (clj->js {:tabs [{:screen screen-name
                                           :title  "Home"
                                           :label  "Home"
                                           :icon   icon}]})))))
