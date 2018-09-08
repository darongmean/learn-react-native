(ns darongmean.state.init
  (:require
    [darongmean.shell :as shell]
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


(defmethod shell/update-context :init
  [_]
  {:state              +state+
   :register-component +screen+
   :get-image-source   +icon+})


(defn initialized? [{:mode/keys [component icon]}]
  (= [:registered :generated] [component icon]))


(defmethod shell/update-context :component-registered
  [_ [m] state]
  (-> state
      (assoc :mode/component :registered)
      (assoc :screen m)
      (transition/goto-listing-when initialized?)))


(defmethod shell/update-context :icon-generated
  [_ [m] state]
  (-> state
      (assoc :mode/icon :generated)
      (assoc :icon m)
      (transition/goto-listing-when initialized?)))

