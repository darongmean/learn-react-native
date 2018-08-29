(ns dev.hot-reload
  (:require
    [citrus.core :as citrus]
    [cljs.pprint :as pprint]
    [darongmean.funan :as funan]))


(defn after-load []
  (citrus/broadcast-sync! funan/reconciler :init))


(defonce _ (add-watch funan/reconciler :debug #(pprint/pprint %4)))


(after-load)
