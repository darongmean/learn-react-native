(ns darongmean.spec
  "Define spec that will be shared across different namespaces."
  (:require
    [cljs.spec.alpha :as s]))


;;; ----------- React Native Navigation
(s/def :rnn/screen string?)
(s/def :rnn/rum-component #(-> % (meta) (:rum/class)))
(s/def
  :rnn/register-component
  (s/map-of keyword?
            (s/keys :req-un [:rnn/screen :rnn/rum-component])))


(s/def :rnn/icon object?)
(s/def :rnn/icon-insets map?)
(s/def :rnn/label string?)
(s/def :rnn/navigator-buttons map?)
(s/def :rnn/navigator-style map?)
(s/def :rnn/selected-icon :rnn/icon)
(s/def :rnn/title string?)
(s/def :rnn/title-image object?)
(s/def
  :rnn/tab
  (s/keys :req-un [:rnn/screen]
          :opt-un [:rnn/icon
                   :rnn/icon-insets
                   :rnn/label
                   :rnn/navigator-buttons
                   :rnn/navigator-style
                   :rnn/selected-icon
                   :rnn/title
                   :rnn/title-image]))
(s/def :rnn/tabs (s/coll-of :rnn/tab :into []))
(s/def
  :rnn/start-tab-based-app
  (s/keys :req-un [:rnn/tabs]))


;;; ------------ React Native Vector Icons
(s/def :rni/name string?)
(s/def :rni/size pos-int?)
(s/def
  :rni/get-image-source
  (s/map-of keyword?
            (s/keys :req-un [:rni/name :rni/size])))


;;; ------------ Citrus
(s/def
  :citrus/context
  (s/keys :req-un [:citrus/state]
          :opt-un [:rni/get-image-source
                   :rnn/register-component
                   :rnn/start-tab-based-app]))
