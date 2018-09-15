(ns darongmean.state.listing.ui
  (:require
    [darongmean.rn.fast-image :as image]
    [darongmean.rn.paper :as paper]
    [darongmean.rn.row :as row]
    [darongmean.shell :as shell]
    [react-native.core :as rn]
    [rum.core :as rum]))


(rum/defc Screen < rum/reactive []
  (paper/Provider {}
    (paper/Card {}
      (paper/CardContent {}
        (paper/Title {} (rum/react (shell/hello-world)))
        (paper/Paragraph {} "Card Content"))
      (paper/CardCover {:source {:uri "https://picsum.photos/700"}})
      (paper/CardActions {}
        (paper/Button {} "Cancel")
        (paper/Button {} "OK")))))
