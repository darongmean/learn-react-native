(ns darongmean.state-machine
  (:require
    [citrus.core :as citrus]
    [darongmean.activity :as activity]
    [darongmean.context :as context]))

;;; --------------- States
(defonce ^:private *app (atom {}))


(def reconciler
  (citrus/reconciler
    {:state           *app
     :controllers     {:context context/update-on}
     :effect-handlers {:do-register-component activity/do-register-component
                       :do-load-icon          activity/do-load-icon
                       :do-show-screen        activity/do-show-screen}}))


;;; --------------- Subscription functions



;;; --------------- Publish event functions
(def broadcast! (partial citrus/broadcast! reconciler))


(def broadcast-sync! (partial citrus/broadcast-sync! reconciler))


