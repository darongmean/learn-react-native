(ns darongmean.rn.vector-icons
  (:refer-clojure :exclude [name])
  (:require
    [cljs.core.async :as async :refer-macros [go]]
    [cljs.spec.alpha :as s]
    [darongmean.shell :as shell]
    [react-native.core :as rn]
    ["react-native-vector-icons/Octicons" :as rnvi]))


(def Icon (rn/react->rum rnvi/default))


(defn icon-chan [[kw {:keys [name size]}]]
  (let [ch (async/chan)]
    (-> rnvi
        (.getImageSource name size)
        (.then #(async/put! ch {kw %1})))
    ch))


(defn get-image-source [_ _ m]
  {:pre [(s/assert :rni/get-image-source m)]}
  (go (->> m
           (map icon-chan)
           (async/map merge)
           (async/<!)
           (shell/broadcast! :icon-initialized))))
