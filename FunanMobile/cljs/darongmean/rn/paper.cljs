(ns darongmean.rn.paper
  (:require
    [react-native.core :as rn]
    ["react-native-paper" :as rnp]))


(def Button (rn/react->rum rnp/Button))

(def Card (rn/react->rum rnp/Card))
(def CardActions (rn/react->rum rnp/Card.Actions))
(def CardContent (rn/react->rum rnp/Card.Content))
(def CardCover (rn/react->rum rnp/Card.Cover))

(def Paragraph (rn/react->rum rnp/Paragraph))

(def Provider (rn/react->rum rnp/Provider))

(def Subheading (rn/react->rum rnp/Subheading))

(def Text (rn/react->rum rnp/Text))

(def Title (rn/react->rum rnp/Title))