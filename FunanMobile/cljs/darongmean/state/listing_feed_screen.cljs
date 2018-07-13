(ns darongmean.state.listing-feed-screen
  (:require [darongmean.state-machine :as stm]))


(defmethod stm/context-on-enter 'ListingFeedScreen [context [icon]]
  (assoc context :icon icon))
