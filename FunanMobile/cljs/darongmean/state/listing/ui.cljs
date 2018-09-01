(ns darongmean.state.listing.ui
  (:require
    [react-native.core :as rn]
    [rum.core :as rum]))


(rum/defc hello-world []
  (rn/text {:style {:font-size   30
                    :font-weight :bold}}
           "Hello, world!"))
