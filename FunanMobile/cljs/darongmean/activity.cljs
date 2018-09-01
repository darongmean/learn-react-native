(ns darongmean.activity
  (:require
    [cljs.core.async :as async :refer-macros [go]]
    [darongmean.state-machine :as stm]
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


(defmethod stm/do-load-icon :default
  [_ _ coll]
  (let [icons (for [[kw options] (seq coll)
                    :let [icon-name (icon-name-by-kw kw)
                          params (-> options (assoc :kw kw) (assoc :icon-name icon-name))]]
                (icon-chan params))]
    (go
      (->> icons
           (async/map merge)
           (async/<!)
           (stm/broadcast! :icon-loaded)))))


(defmethod stm/do-register-component :default
  [_ _ coll]
  (doseq [[kw {:keys [uid]}] (seq coll)
          :let [comp-rum (rum-by-keyword kw)]]
    (.registerComponent Navigation uid (fn [] (:rum/class (meta comp-rum)))))
  (stm/broadcast! :component-registered coll))


(defmethod stm/do-show-screen :default
  [_ _ context]
  (let [screen-key (:mode/screen context)
        screen-name (get-in context [:screen screen-key :uid])
        icon (get-in context [:icon :home])]
    (doto Navigation
      (.startTabBasedApp (clj->js {:tabs [{:screen screen-name
                                           :title  "Home"
                                           :label  "Home"
                                           :icon   icon}]})))))
