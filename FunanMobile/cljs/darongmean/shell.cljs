(ns darongmean.shell)


(defmulti update-context (fn [event _ _] event))


;; Define dynamic method to register with citrus.
;; Abuse of multimethod?


(defmulti broadcast! (fn [& _] :citrus))


(defmulti broadcast-sync! (fn [& _] :citrus))
