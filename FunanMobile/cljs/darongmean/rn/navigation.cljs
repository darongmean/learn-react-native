(ns darongmean.rn.navigation
  (:require
    [cljs.spec.alpha :as s]
    [darongmean.shell :as shell]
    ["react-native-navigation" :refer [Navigation]]))


(defn register-component [_ _ m]
  {:pre [(s/assert :rn.nav/register-component m)]}
  (doseq [[_ {:keys [uid rum-component]}] (seq m)]
    (.registerComponent Navigation uid (fn [] (:rum/class (meta rum-component)))))
  (shell/broadcast! :component-registered m))


(defn start-tab-based-app [_ _ m]
  {:pre [(s/assert :rn.nav/start-tab-based-app m)]}
  (.startTabBasedApp Navigation (clj->js m)))
