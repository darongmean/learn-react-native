(ns darongmean.event)


;;; --------------- Handling event functions
(defmulti update-context (fn [event] event))

