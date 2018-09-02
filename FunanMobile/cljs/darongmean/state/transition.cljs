(ns darongmean.state.transition)


(defn guard [f state pred]
  (if (pred state)
    (f state)
    {:state state}))


(defn goto-listing [state]
  (let [st (assoc state :mode/screen :hello-world)]
    {:state          st
     :do-show-screen st}))


(def goto-listing-when (partial guard goto-listing))
