(ns darongmean.state.transition)


(defn guard [f state pred]
  (if (pred state)
    (f state)
    {:state state}))


(defn goto-listing [{:keys [screen icon] :as state}]
  (let [nav-params {:tabs [{:screen (get-in screen [:listing :uid])
                            :title  "Home"
                            :label  "Home"
                            :icon   (get-in icon [:home])}]}]
    {:state                             (assoc state :mode/screen :listing)
     :rn.navigation/start-tab-based-app nav-params}))


(def goto-listing-when (partial guard goto-listing))
