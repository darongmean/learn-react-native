(ns darongmean.rn.row
  (:require
    [react-native.core :as rn]
    ["react-native-row" :as rnr]))


(def View (rn/react->rum rnr/View))

(def Row (rn/react->rum rnr/default))
