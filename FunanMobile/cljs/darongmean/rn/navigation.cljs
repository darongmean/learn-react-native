(ns darongmean.rn.navigation
  (:require
    [darongmean.activity :as activity]
    [darongmean.state-machine :as stm]
    ["react-native-navigation" :refer [Navigation]]))


(defmethod activity/register-component :default
  [_ _ m]
  (doseq [[_ {:keys [uid rum-component]}] (seq m)]
    (.registerComponent Navigation uid (fn [] (:rum/class (meta rum-component)))))
  (stm/broadcast! :component-registered m))


(defmethod activity/start-tab-based-app :default
  [_ _ m]
  (.startTabBasedApp Navigation (clj->js m)))
