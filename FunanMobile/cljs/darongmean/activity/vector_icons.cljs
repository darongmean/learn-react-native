(ns darongmean.activity.vector-icons
  (:require
    [cljs.core.async :as async :refer-macros [go]]
    [darongmean.activity :as activity]
    [darongmean.state-machine :as stm]
    ["react-native-vector-icons/Octicons" :as Icon]))


(def icon-name-by-kw
  {:home "home"})


(defn icon-chan [{:keys [kw icon-name size]}]
  (let [ch (async/chan)]
    (-> Icon
        (.getImageSource icon-name size)
        (.then #(async/put! ch {kw %1})))
    ch))


(defmethod activity/do-load-icon :default
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
