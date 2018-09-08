(ns darongmean.spec
  "Define spec that will be shared across different namespaces."
  (:require
    [cljs.spec.alpha :as s]))


;;; ----------- Unique ID
(s/def :uid/icon keyword?)
(s/def :uid/icon-ks (s/cat :icon-kw #{:icon} :uid :uid/icon))

(s/def :uid/screen string?)


;;; ----------- React Native Navigation
(s/def :rnn/label string?)
(s/def :rnn/rum-component #(-> % (meta) (:rum/class)))
(s/def :rnn/title string?)
(s/def
  :rnn/register-component
  (s/map-of keyword?
            (s/keys :req-un [:uid/icon-ks
                             :rnn/label
                             :rnn/rum-component
                             :uid/screen
                             :rnn/title])))


(s/def :rnn/icon object?)
(s/def :rnn/icon-insets map?)
(s/def :rnn/navigator-buttons map?)
(s/def :rnn/navigator-style map?)
(s/def :rnn/selected-icon :rnn/icon)
(s/def :rnn/title-image object?)
(s/def
  :rnn/tab
  (s/keys :req-un [:uid/screen]
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
  (s/map-of :uid/icon
            (s/keys :req-un [:rni/name :rni/size])))


;;; ------------ Citrus
(s/def
  :citrus/context
  (s/keys :req-un [:citrus/state]
          :opt-un [:rni/get-image-source
                   :rnn/register-component
                   :rnn/start-tab-based-app]))
