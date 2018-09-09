(ns dev.hot-reload
  (:require
    [cljs.pprint :as pprint]
    [cljs.spec.alpha :as s]
    [darongmean.funan :as funan]
    [darongmean.shell :as shell]
    [darongmean.spec]
    [expound.alpha :as expound]
    [orchestra-cljs.spec.test :as stest]))


(set! s/*explain-out* expound/printer)


(defn refresh-clojure-spec []
  (stest/unstrument)
  (s/check-asserts true)
  (stest/instrument))


(defonce run-once
  (do
    (refresh-clojure-spec)
    (add-watch funan/reconciler :debug #(pprint/pprint %4))
    (funan/main)))


(defn after-load []
  (refresh-clojure-spec)
  ; do some testing
  (shell/broadcast-sync! :init)
  "debugging")


(after-load)
