(ns darongmean.activity.navigation
  (:require
    [darongmean.activity :as activity]
    [darongmean.state-machine :as stm]
    [darongmean.listing.ui :as listing]
    ["react-native-navigation" :refer [Navigation]]))


(def rum-by-keyword
  {:hello-world listing/hello-world})


(defmethod activity/do-register-component :default
  [_ _ coll]
  (doseq [[kw {:keys [uid]}] (seq coll)
          :let [comp-rum (rum-by-keyword kw)]]
    (.registerComponent Navigation uid (fn [] (:rum/class (meta comp-rum)))))
  (stm/broadcast! :component-registered coll))


(defmethod activity/do-show-screen :default
  [_ _ context]
  (let [screen-key (:mode/screen context)
        screen-name (get-in context [:screen screen-key :uid])
        icon (get-in context [:icon :home])]
    (doto Navigation
      (.startTabBasedApp (clj->js {:tabs [{:screen screen-name
                                           :title  "Home"
                                           :label  "Home"
                                           :icon   icon}]})))))
