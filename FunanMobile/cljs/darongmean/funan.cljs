(ns darongmean.funan
  (:require
    [darongmean.rn.navigation]
    [darongmean.rn.vector-icons]
    [darongmean.state.init]
    [darongmean.state-machine :as stm]))


(enable-console-print!)


(defn main []
  (stm/broadcast-sync! :init))
