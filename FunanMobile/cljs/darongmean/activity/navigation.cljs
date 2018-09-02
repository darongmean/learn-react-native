(ns darongmean.activity.navigation
  (:require
    [darongmean.activity :as activity]
    [darongmean.state-machine :as stm]
    ["react-native-navigation" :refer [Navigation]]))


(defmethod activity/do-register-component :default
  [_ _ coll]
  (doseq [[_ {:keys [uid rum-component]}] (seq coll)]
    (.registerComponent Navigation uid (fn [] (:rum/class (meta rum-component)))))
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
