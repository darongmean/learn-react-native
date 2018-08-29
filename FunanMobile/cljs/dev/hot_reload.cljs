(ns dev.hot-reload
  (:require
    [citrus.core :as citrus]
    [cljs.pprint :as pprint]
    [darongmean.funan :as funan]))


(defn after-load []
  (citrus/broadcast! funan/reconciler :RUN-FOREGROUND)
  (pprint/pprint @funan/reconciler))


(after-load)
