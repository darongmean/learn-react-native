(ns darongmean.spec
  "Define spec that will be shared across different namespaces."
  (:require
    [cljs.spec.alpha :as s]))


;;; ----------- React Native Navigation
(s/def :rnn/uid string?)

(s/def :rnn/rum-component #(-> % (meta) (:rum/class)))

(s/def
  :rn.nav/register-component
  (s/map-of keyword?
            (s/keys :req-un [:rnn/uid :rnn/rum-component])))


; TODO: use only one?
(s/def :rnn/screen :rnn/uid)

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
  :rn.nav/start-tab-based-app
  (s/keys :req-un [:rnn/tabs]))


;;; ------------ Citrus
(s/def
  :citrus/context
  (s/keys :req-un [:citrus/state]
          :opt [:rn.nav/register-component
                :rn.nav/start-tab-based-app]))
