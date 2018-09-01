(ns darongmean.funan
  (:require
    [darongmean.activity.react-native]
    [darongmean.event :as context]
    [darongmean.state-machine :as stm]))


(enable-console-print!)


; Event-Action table?
(def state-chart-transitions
  {'Initial           {:on {:RUN-FOREGROUND 'Loading}}
   'Loading           {:on       {:SHOW-SCREEN 'ListingFeedScreen}
                       :on-enter [:do-register-component :do-load-icon]}
   'ListingFeedScreen {:on-enter [:do-show-screen]}})


;; transition context -> [new-context actions]
;(defn transition [event args context])
;[context {}])


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


(defmethod context/update-context :init
  [_]
  {:state                 +init+
   :do-register-component {:hello-world {:uid "darong.funan.hello-world"}}
   :do-load-icon          {:home {:size 30}}})


(defmethod context/update-context :component-registered
  [_ [coll] state]
  (let [new-sate (-> state
                     (assoc :mode/component :component/registered)
                     (assoc :screen coll))]
    (show-screen new-sate)))


(defmethod context/update-context :icon-loaded
  [_ [coll] state]
  (let [new-state (-> state
                      (assoc :mode/icon :icon/loaded)
                      (assoc :icon coll))]
    (show-screen new-state)))


(defn main []
  (stm/broadcast-sync! :init))


(defonce _ (main))
