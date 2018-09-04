(ns darongmean.funan
  (:require
    [citrus.core :as citrus]
    [darongmean.rn.navigation :as rnn]
    [darongmean.rn.vector-icons :as rni]
    [darongmean.shell :as shell]
    [darongmean.state.init]))


(enable-console-print!)


;;; --------------- States
(defonce ^:private *app (atom {}))


(def reconciler
  (citrus/reconciler
    {:state           *app
     :controllers     {:context shell/update-context}
     :effect-handlers {:rn.icon/get-image-source   rni/get-image-source
                       :rn.nav/register-component  rnn/register-component
                       :rn.nav/start-tab-based-app rnn/start-tab-based-app}}))


;;; --------------- Subscription functions



;;; --------------- Publish event functions
(defmethod shell/broadcast! :citrus
  [event & args]
  (apply (partial citrus/broadcast! reconciler event) args))


(defmethod shell/broadcast-sync! :citrus
  [event & args]
  (apply (partial citrus/broadcast-sync! reconciler event) args))


;;; -------------- Entry point
(defn main []
  (shell/broadcast-sync! :init))
