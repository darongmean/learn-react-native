(ns darongmean.rn.vector-icons
  (:refer-clojure :exclude [name])
  (:require
    [cljs.core.async :as async :refer-macros [go]]
    [cljs.spec.alpha :as s]
    [darongmean.shell :as shell]
    ["react-native-vector-icons/Octicons" :as Icon]))


(defn icon-chan [[kw {:keys [name size]}]]
  (let [ch (async/chan)]
    (-> Icon
        (.getImageSource name size)
        (.then #(async/put! ch {kw %1})))
    ch))


(defn get-image-source [_ _ m]
  {:pre [(s/assert :rn.icon/get-image-source m)]}
  (go (->> m
           (map icon-chan)
           (async/map merge)
           (async/<!)
           (shell/broadcast! :icon-generated))))
