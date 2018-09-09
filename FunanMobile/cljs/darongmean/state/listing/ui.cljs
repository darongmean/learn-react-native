(ns darongmean.state.listing.ui
  (:require
    [darongmean.shell :as shell]
    [react-native.core :as rn]
    [rum.core :as rum]))


(rum/defc screen < rum/reactive []
  (rn/text {:style {:font-size   30
                    :font-weight :bold}}
           (rum/react (shell/hello-world))))
