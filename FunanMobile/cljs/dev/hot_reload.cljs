(ns dev.hot-reload
  (:require
    [cljs.pprint :as pprint]
    [darongmean.funan :as funan]
    [darongmean.state-machine :as stm]))


(defonce _ (add-watch stm/reconciler :debug #(pprint/pprint %4)))


(defonce _ (funan/main))


(defn after-load []
  (stm/broadcast-sync! :init))


(after-load)
