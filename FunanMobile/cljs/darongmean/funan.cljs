(ns darongmean.funan
  (:require
    [darongmean.state-machine :as stm]
    [darongmean.activity :as activity]
    [darongmean.state.listing-feed-screen]
    [citrus.core :as citrus]))


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
  {:mode/screen    :screen/init
   :mode/component :component/init
   :mode/icon      :icon/init})


(defn show-screen [state]
  (if (= [:component/registered :icon/loaded]
         [(:mode/component state) (:mode/icon state)])
    {:state state :do-show-screen state}
    {:state state}))


(defn update-context [event args state]
  (cond
    (= :init event)
    {:state                 +init+
     :do-register-component +init+
     :do-load-icon          +init+}

    (= :component-registered event)
    (let [new-sate (assoc state :mode/component :component/registered)]
      (show-screen new-sate))

    (= :icon-loaded event)
    (let [[k icon] args
          new-state (-> state
                        (assoc :mode/icon :icon/loaded)
                        (assoc-in [:icon k] icon))]
      (show-screen new-state))))


(defonce ^:private *app (atom {}))


(def reconciler
  (citrus/reconciler
    {:state           *app
     :controllers     {:context update-context}
     :effect-handlers {:do-register-component activity/do-register-component
                       :do-load-icon          activity/do-load-icon
                       :do-show-screen        activity/do-show-screen}}))


(defn main []
  (citrus/broadcast-sync! reconciler :init))


(defonce _ (main))
