(ns darongmean.funan
  (:require
    [darongmean.activity.navigation]
    [darongmean.activity.vector-icons]
    [darongmean.state.init]
    [darongmean.state-machine :as stm]))


(enable-console-print!)


(defn main []
  (stm/broadcast-sync! :init))
