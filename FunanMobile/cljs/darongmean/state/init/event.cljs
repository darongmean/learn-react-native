(ns darongmean.state.init.event
  (:require
    [darongmean.context :as context]
    [darongmean.state.transition :as transition]))


(def +init+
  {:mode/screen    :init
   :mode/component :init
   :mode/icon      :init})


(defmethod context/update-on :init
  [_]
  {:state                 +init+
   :do-register-component {:hello-world {:uid "darong.funan.hello-world"}}
   :do-load-icon          {:home {:size 30}}})


(defn initialized? [state]
  (= [:component/registered :icon/loaded]
     [(:mode/component state) (:mode/icon state)]))


(defmethod context/update-on :component-registered
  [_ [coll] state]
  (-> state
      (assoc :mode/component :component/registered)
      (assoc :screen coll)
      (transition/goto-listing initialized?)))


(defmethod context/update-on :icon-loaded
  [_ [coll] state]
  (-> state
      (assoc :mode/icon :icon/loaded)
      (assoc :icon coll)
      (transition/goto-listing initialized?)))

