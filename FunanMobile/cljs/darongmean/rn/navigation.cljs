(ns darongmean.rn.navigation
  (:require
    [cljs.spec.alpha :as s]
    [darongmean.shell :as shell]
    ["react-native-navigation" :refer [Navigation]]))


(defn register-component [_ _ m]
  {:pre [(s/assert :rnn/register-component m)]}
  (doseq [[_ {:keys [screen rum-component]}] (seq m)]
    (.registerComponent Navigation screen (fn [] (:rum/class (meta rum-component)))))
  (shell/broadcast! :layout-initialized m))


(defn start-tab-based-app [_ _ m]
  {:pre [(s/assert :rnn/start-tab-based-app m)]}
  (.startTabBasedApp Navigation (clj->js m)))
