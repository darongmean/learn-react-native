(ns darongmean.activity.vector-icons
  (:refer-clojure :exclude [name])
  (:require
    [cljs.core.async :as async :refer-macros [go]]
    [darongmean.activity :as activity]
    [darongmean.state-machine :as stm]
    ["react-native-vector-icons/Octicons" :as Icon]))


(defn icon-chan [[kw {:keys [name size]}]]
  (let [ch (async/chan)]
    (-> Icon
        (.getImageSource name size)
        (.then #(async/put! ch {kw %1})))
    ch))


(defmethod activity/get-image-source :default
  [_ _ m]
  (go (->> m
           (map icon-chan)
           (async/map merge)
           (async/<!)
           (stm/broadcast! :icon-generated))))
