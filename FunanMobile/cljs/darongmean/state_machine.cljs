(ns darongmean.state-machine
  (:require
    [citrus.core :as citrus]))

;;; --------------- Handling event functions
(defmulti update-context (fn [event] event))

;;; --------------- Side effect functions
(defmulti do-register-component (fn [_ ctrl _] ctrl))


(defmulti do-load-icon (fn [_ ctrl _] ctrl))


(defmulti do-show-screen (fn [_ ctrl _] ctrl))


;;; --------------- States
(defonce ^:private *app (atom {}))


(def reconciler
  (citrus/reconciler
    {:state           *app
     :controllers     {:context update-context}
     :effect-handlers {:do-register-component do-register-component
                       :do-load-icon          do-load-icon
                       :do-show-screen        do-show-screen}}))


;;; --------------- Subscription functions



;;; --------------- Publish event functions
(def broadcast! (partial citrus/broadcast! reconciler))


(def broadcast-sync! (partial citrus/broadcast-sync! reconciler))


