(ns darongmean.activity)


(defmulti register-component (fn [_ ctrl _] ctrl))


(defmulti get-image-source (fn [_ ctrl _] ctrl))


(defmulti start-tab-based-app (fn [_ ctrl _] ctrl))
