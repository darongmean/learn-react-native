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


(def icon-name-by-kw
  {:home "home"})


(defn do-load-icon [rr _ coll]
  (doseq [[kw {:keys [size]}] (seq coll)]
    (-> Icon
        (.getImageSource (icon-name-by-kw kw) size)
        (.then #(citrus/broadcast! rr :icon-loaded kw %1)))))


(defn do-register-component [rr _ coll]
  (doseq [[kw {:keys [uid]}] (seq coll)
          :let [comp-rum (rum-by-keyword kw)]]
    (.registerComponent Navigation uid (fn [] (:rum/class (meta comp-rum)))))
  (citrus/broadcast! rr :component-registered coll))


(defn do-show-screen [_ _ context]
  (let [screen-key (:mode/screen context)
        screen-name (get-in context [:screen screen-key :uid])
        icon (get-in context [:icon :home])]
    (doto Navigation
      (.startTabBasedApp (clj->js {:tabs [{:screen screen-name
                                           :title  "Home"
                                           :label  "Home"
                                           :icon   icon}]})))))
