(ns darongmean.state.init.event
  (:require
    [darongmean.context :as context]))


(def +init+
  {:mode/screen    :init
   :mode/component :init
   :mode/icon      :init})


(defn show-screen [state]
  (if (= [:component/registered :icon/loaded]
         [(:mode/component state) (:mode/icon state)])
    (let [st (assoc state :mode/screen :hello-world)]
      {:state st :do-show-screen st})
    {:state state}))


(defmethod context/update-on :init
  [_]
  {:state                 +init+
   :do-register-component {:hello-world {:uid "darong.funan.hello-world"}}
   :do-load-icon          {:home {:size 30}}})


(defmethod context/update-on :component-registered
  [_ [coll] state]
  (-> state
      (assoc :mode/component :component/registered)
      (assoc :screen coll)
      (show-screen)))


(defmethod context/update-on :icon-loaded
  [_ [coll] state]
  (-> state
      (assoc :mode/icon :icon/loaded)
      (assoc :icon coll)
      (show-screen)))

