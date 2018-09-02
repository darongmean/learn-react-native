(ns darongmean.activity.vector-icons
  (:refer-clojure :exclude [name])
  (:require
    [cljs.core.async :as async :refer-macros [go]]
    [darongmean.activity :as activity]
    [darongmean.state-machine :as stm]
    ["react-native-vector-icons/Octicons" :as Icon]))


(defn icon-chan [kw {:keys [name size]}]
  (let [ch (async/chan)]
    (-> Icon
        (.getImageSource name size)
        (.then #(async/put! ch {kw %1})))
    ch))


(defmethod activity/get-image-source :default
  [_ _ coll]
  (let [icon-chans (map #(icon-chan %1 %2) coll)
        as-hash-map #(->> % (async/map merge) (async/<!))]
    (go
      (->> icon-chans
           (as-hash-map)
           (stm/broadcast! :icon-generated)))))
