(ns darongmean.spec
  "Define spec that will be shared across different namespaces."
  (:require
    [cljs.spec.alpha :as s]))


(s/def :rnn/uid string?)

(s/def :rnn/rum-component #(-> % (meta) (:rum/class)))

(s/def
  :rn.nav/register-component (s/map-of keyword?
                                       (s/keys :req-un [:rnn/uid :rnn/rum-component])))
