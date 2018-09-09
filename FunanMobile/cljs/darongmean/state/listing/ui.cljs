(ns darongmean.state.listing.ui
  (:require
    [darongmean.rn.fast-image :as image]
    [darongmean.rn.row :as row]
    [darongmean.rn.ui-kitten :as kitten]
    [darongmean.shell :as shell]
    [react-native.core :as rn]
    [rum.core :as rum]))


(rum/defc Screen < rum/reactive []
  (row/View {:dial 5}
    (rn/Text {:style {:font-size 30 :font-weight :bold}}
      (rum/react (shell/hello-world)))
    (kitten/RkButton {} "Click Me!")
    (image/FastImage {:style       {:width 200 :height 200}
                      :source      {:uri "https://unsplash.it/400/400?image=1"}
                      :priority    image/priority-normal
                      :resize-mode image/resize-mode-contain})))
