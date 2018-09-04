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
  {:home {:name "home" :size 30}})


(defmethod context/update-on :init
  [_]
  {:state                            +state+
   :rn.navigation/register-component +screen+
   :rn.icon/get-image-source         +icon+})


(defn initialized? [{:mode/keys [component icon]}]
  (= [:registered :generated] [component icon]))


(defmethod context/update-on :component-registered
  [_ [m] state]
  (-> state
      (assoc :mode/component :registered)
      (assoc :screen m)
      (transition/goto-listing-when initialized?)))


(defmethod context/update-on :icon-generated
  [_ [m] state]
  (-> state
      (assoc :mode/icon :generated)
      (assoc :icon m)
      (transition/goto-listing-when initialized?)))

