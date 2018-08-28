(ns dev.hot-reload
  (:require
    [cljs.pprint :as pprint]
    [darongmean.funan :as funan]))


(defn after-load []
  (pprint/pprint @funan/reconciler))


(after-load)
