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


(defmethod stm/dispatch-on-event :init []
  {:state {:state-id 'Initial :state-chart state-chart-transitions}})


(defonce reconciler
  (citrus/reconciler
    {:state
     (atom {})
     :controllers
     {:context stm/dispatch-on-event}
     :effect-handlers
     {:do-register-component activity/do-register-component
      :do-load-icon          activity/do-load-icon
      :do-show-screen        activity/do-show-screen}}))


;(defonce init-ctrl (citrus/broadcast-sync! reconciler :init))


(defn main []
  (citrus/broadcast-sync! reconciler :init)
  (citrus/broadcast! reconciler :RUN-FOREGROUND))


(defonce _ (main))
