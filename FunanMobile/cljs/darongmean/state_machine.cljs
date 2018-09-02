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
     :effect-handlers {:icon/get-image-source          activity/get-image-source
                       :navigation/register-component  activity/register-component
                       :navigation/start-tab-based-app activity/start-tab-based-app}}))


;;; --------------- Subscription functions



;;; --------------- Publish event functions
(def broadcast! (partial citrus/broadcast! reconciler))


(def broadcast-sync! (partial citrus/broadcast-sync! reconciler))


