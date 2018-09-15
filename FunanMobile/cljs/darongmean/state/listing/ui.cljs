(ns darongmean.state.listing.ui
  (:require
    [darongmean.rn.fast-image :as image]
    [darongmean.rn.paper :as paper]
    [darongmean.rn.row :as row]
    [darongmean.shell :as shell]
    [darongmean.rn.vector-icons :as icon]
    [react-native.core :as rn]
    [rum.core :as rum]))


(rum/defc Item < rum/reactive [_]
  (paper/Card {:style {:width 175 :height 175}}
    (paper/CardCover {:source {:uri "https://picsum.photos/700"}
                      :style  {:height 150}})
    (paper/CardContent {}
      (paper/Paragraph {}
        (icon/Icon {:name "info" :size 15})
        (rum/react (shell/hello-world))))))


(rum/defc Screen < rum/reactive []
  (paper/Provider {}
    (rn/FlatList {:data               (range 1 7)
                  :keyExtractor       (fn [idx _] idx)
                  :numColumns         2
                  :columnWrapperStyle {:flex            1
                                       :flexDirection   "row"
                                       :justify-content "space-evenly"
                                       :margin          4}
                  :renderItem         (fn [jsobj] (Item jsobj))})))
