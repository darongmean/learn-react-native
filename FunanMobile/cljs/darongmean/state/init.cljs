(ns darongmean.state.init
  (:require
    [darongmean.shell :as shell]
    [darongmean.state.listing.ui :as listing]
    [darongmean.state.transition :as transition]))


(def +state+
  {:mode/layout :init
   :mode/icon   :init})


(def +screen+
  {:listing {:icon-ks       [:icon :home]
             :label         "Home"
             :rum-component listing/Screen
             :screen        "listing"
             :title         "Screen Home"}})


(def +icon+
  {:home {:name "home" :size 30}})


(defmethod shell/update-context :init
  [_]
  {:state              +state+
   :register-component +screen+
   :get-image-source   +icon+})


(defn initialized? [{:mode/keys [layout icon]}]
  (= [:initialized :initialized] [layout icon]))


(defmethod shell/update-context :layout-initialized
  [_ [m] state]
  (-> state
      (assoc :mode/layout :initialized)
      (assoc :layout m)
      (transition/goto-listing-when initialized?)))


(defmethod shell/update-context :icon-initialized
  [_ [m] state]
  (-> state
      (assoc :mode/icon :initialized)
      (assoc :icon m)
      (transition/goto-listing-when initialized?)))

