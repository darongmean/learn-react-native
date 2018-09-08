(ns darongmean.state.transition)


(defn guard [f state pred]
  (if (pred state)
    (f state)
    {:state state}))


(defn goto-listing [{:keys [layout icon] :as state}]
  (let [listing-layout {:screen (get-in layout [:listing :screen])
                        :title  "Home"
                        :label  "Home"
                        :icon   (get-in icon [:home])}
        nav-params {:tabs [listing-layout]}
        listing-layout-mode (assoc state :mode/screen :listing)]
    {:state               listing-layout-mode
     :start-tab-based-app nav-params}))


(def goto-listing-when (partial guard goto-listing))
