(ns darongmean.rn.fast-image
  (:require
    [react-native.core :as rn]
    ["react-native-fast-image" :as rnfi]))


(def FastImage (rn/react->rum rnfi/default))

(def priority (js->clj rnfi/default.priority))
(def priority-low (:low priority))
(def priority-normal (:normal priority))
(def priority-high (:high priority))

(def resize-mode (js->clj rnfi/default.resizeMode))
(def resize-mode-contain (:contain resize-mode))
(def resize-mode-cover (:cover resize-mode))
(def resize-mode-stretch (:stretch resize-mode))
(def resize-mode-center (:center resize-mode))