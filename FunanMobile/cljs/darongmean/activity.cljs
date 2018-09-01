(ns darongmean.activity
  (:require
    [citrus.core :as citrus]
    [cljs.core.async :as async :refer-macros [go]]
    [react-native.core :as rn]
    ["react-native-navigation" :refer [Navigation]]
    ["react-native-vector-icons/Octicons" :as Icon]
    [rum.core :as rum]))


(rum/defc hello-world []
  (rn/text {:style {:font-size   30
                    :font-weight :bold}}
           "Hello, world!"))


(def rum-by-keyword
  {:hello-world hello-world})


(def icon-name-by-kw
  {:home "home"})


(defn icon-chan [{:keys [kw icon-name size]}]
  (let [ch (async/chan)]
    (-> Icon
        (.getImageSource icon-name size)
        (.then #(async/put! ch {kw %1})))
    ch))


(defn do-load-icon [rr _ coll]
  (let [icons (for [[kw options] (seq coll)
                    :let [icon-name (icon-name-by-kw kw)
                          params (-> options (assoc :kw kw) (assoc :icon-name icon-name))]]
                (icon-chan params))]
    (go
      (->> icons
           (async/map merge)
           (async/<!)
           (citrus/broadcast! rr :icon-loaded)))))


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
