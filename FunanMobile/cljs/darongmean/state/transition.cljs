(ns darongmean.state.transition)


(defn guard [f state pred]
  (if (pred state)
    (f state)
    {:state state}))


(defn update-layout-mode [state kw]
  (assoc state :mode/layout kw))


(defn listing-layout-tabs [{{screen :screen} :listing} {:keys [home]}]
  (let [listing-layout {:screen screen
                        :title  "Home"
                        :label  "Home"
                        :icon   home}]
    {:tabs [listing-layout]}))


(defn goto-listing [{:keys [layout icon] :as state}]
  {:state               (update-layout-mode state :listing)
   :start-tab-based-app (listing-layout-tabs layout icon)})


(def goto-listing-when (partial guard goto-listing))
