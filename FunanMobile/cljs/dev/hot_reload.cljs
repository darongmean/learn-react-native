(ns dev.hot-reload
  (:require
    [cljs.pprint :as pprint]
    [darongmean.funan :as funan]
    [darongmean.shell :as shell]))


(defonce _ (add-watch funan/reconciler :debug #(pprint/pprint %4)))


(defonce _ (funan/main))


(defn after-load []
  "debugging")


(after-load)
