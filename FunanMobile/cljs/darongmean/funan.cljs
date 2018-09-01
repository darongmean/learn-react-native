(ns darongmean.funan
  (:require
    [darongmean.activity.react-native]
    [darongmean.init.event]
    [darongmean.state-machine :as stm]))


(enable-console-print!)


(defn main []
  (stm/broadcast-sync! :init))


(defonce _ (main))
