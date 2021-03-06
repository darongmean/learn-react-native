(ns darongmean.funan
  (:require
    [citrus.core :as citrus]
    [cljs.spec.alpha :as s]
    [darongmean.rn.navigation :as rnn]
    [darongmean.rn.vector-icons :as rni]
    [darongmean.shell :as shell]
    [darongmean.state.init]))


(enable-console-print!)


;;; --------------- States
(defonce ^:private *app (atom {}))


(defn update-context
  "A work around to run clojure.spec because
  `spec` can't apply to anonymous function maintained by Citrus."
  [event ctrl args]
  {:post [(s/assert :citrus/context %)]}
  (shell/update-context event ctrl args))


(def reconciler
  (citrus/reconciler
    {:state           *app
     :controllers     {:context update-context}
     :effect-handlers {:get-image-source    rni/get-image-source
                       :register-component  rnn/register-component
                       :start-tab-based-app rnn/start-tab-based-app}}))


;;; --------------- Subscription functions
(defmethod shell/hello-world :citrus
  [_]
  (citrus/subscription reconciler [:mode/layout] (fn [_] "Hello Citrus!")))



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
