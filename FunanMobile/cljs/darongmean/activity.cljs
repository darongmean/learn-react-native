(ns darongmean.activity)


(defmulti do-register-component (fn [_ ctrl _] ctrl))


(defmulti do-load-icon (fn [_ ctrl _] ctrl))


(defmulti do-show-screen (fn [_ ctrl _] ctrl))
