(ns darongmean.state.listing.ui
  (:require
    [darongmean.rn.ui-kitten :as uikit]
    [darongmean.shell :as shell]
    [react-native.core :as rn]
    [rum.core :as rum]))


(rum/defc Screen < rum/reactive []
  (rn/view {}
    (rn/text {:style {:font-size   30
                      :font-weight :bold}}
      (rum/react (shell/hello-world)))
    (uikit/RkButton {} "Click Me!")))
