(ns darongmean.state-machine
  (:require
    [darongmean.activity :as activity]
    [citrus.core :as citrus]))


(defmulti update-context (fn [event-signal] event-signal))


(defonce ^:private *app (atom {}))


(def reconciler
  (citrus/reconciler
    {:state           *app
     :controllers     {:context update-context}
     :effect-handlers {:do-register-component activity/do-register-component
                       :do-load-icon          activity/do-load-icon
                       :do-show-screen        activity/do-show-screen}}))


(def broadcast! (partial citrus/broadcast! reconciler))


(def broadcast-sync! (partial citrus/broadcast-sync! reconciler))


