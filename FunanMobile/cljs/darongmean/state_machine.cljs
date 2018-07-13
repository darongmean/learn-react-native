(ns darongmean.state-machine)


(defmulti context-on-enter (fn [context event-params] (:state-id context)))


(defmethod context-on-enter :default [m _] m)


(defn activity-on-enter [app-state]
  (let [{:keys [state-id state-chart] :as context} (:state app-state)
        activity-id-coll (get-in state-chart [state-id :on-enter])
        activity (->> (repeat context)
                      (interleave activity-id-coll)
                      (apply hash-map))]
    (merge app-state activity)))


(defmulti dispatch-on-event (fn [event-signal] event-signal))


(defmethod dispatch-on-event :default
  [event-signal event-params {:keys [state-id state-chart] :as context}]
  (let [next-state-id (get-in state-chart [state-id :on event-signal])
        transition (assoc context :state-id next-state-id)
        next-context (context-on-enter transition event-params)
        next-state {:state next-context}]
    (if next-state-id
      (activity-on-enter next-state)
      {:state context})))
