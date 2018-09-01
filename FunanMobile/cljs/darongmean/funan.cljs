(ns darongmean.funan
  (:require
    [darongmean.activity.navigation]
    [darongmean.activity.vector-icons]
    [darongmean.init.event]
    [darongmean.state-machine :as stm]))


(enable-console-print!)


(defn main []
  (stm/broadcast-sync! :init))


(defonce _ (main))
