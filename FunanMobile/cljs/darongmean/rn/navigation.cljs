(ns darongmean.rn.navigation
  (:require
    [darongmean.shell :as shell]
    ["react-native-navigation" :refer [Navigation]]))


(defn register-component [_ _ m]
  (doseq [[_ {:keys [uid rum-component]}] (seq m)]
    (.registerComponent Navigation uid (fn [] (:rum/class (meta rum-component)))))
  (shell/broadcast! :component-registered m))


(defn start-tab-based-app [_ _ m]
  (.startTabBasedApp Navigation (clj->js m)))
