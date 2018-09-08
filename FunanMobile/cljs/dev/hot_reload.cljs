(ns dev.hot-reload
  (:require
    [cljs.pprint :as pprint]
    [cljs.spec.alpha :as s]
    [darongmean.funan :as funan]
    [darongmean.shell :as shell]
    [darongmean.spec]
    [expound.alpha :as expound]
    [orchestra-cljs.spec.test :as stest]))


(defonce _ (add-watch funan/reconciler :debug #(pprint/pprint %4)))

(set! s/*explain-out* expound/printer)


(defn after-load []
  ; activate clojure.spec
  (stest/unstrument)
  (s/check-asserts true)
  (stest/instrument)
  ; do some testing
  (shell/broadcast-sync! :init)
  "debugging")


(after-load)
