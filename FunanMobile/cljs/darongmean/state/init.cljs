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
  {:state                         +state+
   :navigation/register-component +screen+
   :icon/get-image-source         +icon+})


(defn initialized? [state]
  (= [:registered :generated]
     [(:mode/component state) (:mode/icon state)]))


(defmethod context/update-on :component-registered
  [_ [coll] state]
  (-> state
      (assoc :mode/component :registered)
      (assoc :screen coll)
      (transition/goto-listing-when initialized?)))


(defmethod context/update-on :icon-generated
  [_ [coll] state]
  (-> state
      (assoc :mode/icon :generated)
      (assoc :icon coll)
      (transition/goto-listing-when initialized?)))

