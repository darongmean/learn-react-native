(ns darongmean.state.init
  (:require
    [darongmean.context :as context]
    [darongmean.state.listing.ui :as listing]
    [darongmean.state.transition :as transition]))


(def +state+
  {:mode/screen    :init
   :mode/component :init
   :mode/icon      :init})


(def +screen+
  {:listing {:uid           "listing"
             :rum-component listing/screen}})


(def +icon+
  {:home {:size 30}})


(defmethod context/update-on :init
  [_]
  {:state                 +state+
   :do-register-component +screen+
   :do-load-icon          +icon+})


(defn initialized? [state]
  (= [:component/registered :icon/loaded]
     [(:mode/component state) (:mode/icon state)]))


(defmethod context/update-on :component-registered
  [_ [coll] state]
  (-> state
      (assoc :mode/component :component/registered)
      (assoc :screen coll)
      (transition/goto-listing-when initialized?)))


(defmethod context/update-on :icon-loaded
  [_ [coll] state]
  (-> state
      (assoc :mode/icon :icon/loaded)
      (assoc :icon coll)
      (transition/goto-listing-when initialized?)))

