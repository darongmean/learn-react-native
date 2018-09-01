(ns darongmean.context)


(defmulti update-on (fn [event] event))
