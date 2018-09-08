(ns dev.hot-reload
  (:require
    [cljs.pprint :as pprint]
    [cljs.spec.alpha :as s]
    [cljs.spec.test.alpha :as stest]
    [darongmean.funan :as funan]
    [darongmean.shell :as shell]
    [darongmean.spec]
    [expound.alpha :as expound]))


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
