(ns darongmean.state.transition)


(defn guard [f state pred]
  (if (pred state)
    (f state)
    {:state state}))


(defn update-layout-mode [state kw]
  (assoc state :mode/layout kw))


(defn as-tab-with-icon [state kw]
  (let [{:keys [icon-ks] :as m} (get-in state [:layout kw])
        icon (get-in state icon-ks)]
    (-> m
        (assoc-in [:icon] icon)
        (dissoc :rum-component))))


(defn listing-mode-tabs [state]
  (let [listing-tab (as-tab-with-icon state :listing)]
    {:tabs [listing-tab]}))


(defn goto-listing [state]
  {:state               (update-layout-mode state :listing)
   :start-tab-based-app (listing-mode-tabs state)})


(def goto-listing-when (partial guard goto-listing))
