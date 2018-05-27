(ns darongmean.state-machine)


(defmulti render :state)


(defmulti apply-action-on-enter (fn [context _] (:state context)))
