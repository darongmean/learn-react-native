(ns darongmean.activity.navigation
  (:require
    [darongmean.activity :as activity]
    [darongmean.state-machine :as stm]
    ["react-native-navigation" :refer [Navigation]]))


(defmethod activity/register-component :default
  [_ _ coll]
  (doseq [[_ {:keys [uid rum-component]}] (seq coll)]
    (.registerComponent Navigation uid (fn [] (:rum/class (meta rum-component)))))
  (stm/broadcast! :component-registered coll))


(defmethod activity/start-tab-based-app :default
  [_ _ coll]
  (.startTabBasedApp Navigation (clj->js coll)))
