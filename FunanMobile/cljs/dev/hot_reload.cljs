(ns dev.hot-reload
  (:require
    [cljs.pprint :as pprint]
    [darongmean.funan]
    [darongmean.state-machine :as stm]))


(defn after-load []
  (stm/broadcast-sync! :init))


(defonce _ (add-watch stm/reconciler :debug #(pprint/pprint %4)))


(after-load)
