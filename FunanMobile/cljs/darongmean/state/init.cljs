(ns darongmean.state.init
  (:require
    [darongmean.shell :as shell]
    [darongmean.state.listing.ui :as listing]
    [darongmean.state.transition :as transition]))


(def +state+
  {:mode/layout :init
   :mode/icon   :init})


(def +screen+
  {:listing {:screen        "listing"
             :rum-component listing/screen}})


(def +icon+
  {:home {:name "home" :size 30}})


(defmethod shell/update-context :init
  [_]
  {:state              +state+
   :register-component +screen+
   :get-image-source   +icon+})


(defn initialized? [{:mode/keys [layout icon]}]
  (= [:registered :generated] [layout icon]))


(defmethod shell/update-context :layout-registered
  [_ [m] state]
  (-> state
      (assoc :mode/layout :registered)
      (assoc :layout m)
      (transition/goto-listing-when initialized?)))


(defmethod shell/update-context :icon-generated
  [_ [m] state]
  (-> state
      (assoc :mode/icon :generated)
      (assoc :icon m)
      (transition/goto-listing-when initialized?)))

