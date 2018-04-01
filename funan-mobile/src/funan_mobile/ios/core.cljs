(ns funan-mobile.ios.core
  (:require [react]
            [react-native :as rn]
            [re-natal.support :as support]
            [rum.core :as rum])
  (:require-macros [rum.core :refer [defc]]))

(set! js/window.React react)

(defn create-element [rn-comp opts & children]
  (apply react/createElement rn-comp (clj->js opts) children))

(def app-registry rn/AppRegistry)
(def view (partial create-element rn/View))
(def text (partial create-element rn/Text))
(def image (partial create-element rn/Image))
(def touchable-highlight (partial create-element rn/TouchableHighlight))

(def logo-img (js/require "./images/cljs.png"))

(defn alert [title]
  (.alert rn/Alert title))

(defonce app-state (atom {:greeting "Hello Clojure in iOS and Android!"}))

(def RNMasonry (js/require "react-native-masonry"))
(def RNFastImage (js/require "react-native-fast-image"))

(def masonry (partial create-element (.-default RNMasonry)))
(def fast-image (partial create-element (.-default RNFastImage)))

(defc AppRoot < rum/reactive [state]
  (view {:style {:flex 1 :flexGrow 10 :padding 5}}
        (masonry {:columns 2
                  :customImageComponent (.-default RNFastImage)
                  :bricks  [{:id 1 :uri "https://pbs.twimg.com/media/B59AOmICQAAiGGj.png"}
                            {:id 2 :uri "https://pbs.twimg.com/media/B59AOmICQAAiGGj.png"}
                            {:id 3 :uri "https://pbs.twimg.com/media/B59AOmICQAAiGGj.png"}
                            {:id 4 :uri "https://pbs.twimg.com/media/B59AOmICQAAiGGj.png"}
                            {:id 5 :uri "https://pbs.twimg.com/media/B59AOmICQAAiGGj.png"}
                            {:id 6 :uri "https://pbs.twimg.com/media/B59AOmICQAAiGGj.png"}
                            {:id 7 :uri "https://pbs.twimg.com/media/B59AOmICQAAiGGj.png"}
                            {:id 8 :uri "https://pbs.twimg.com/media/B59AOmICQAAiGGj.png"}]})))

(defonce root-component-factory (support/make-root-component-factory))

(defn mount-app [] (support/mount (AppRoot app-state)))

(defn init []
  (mount-app)
  (.registerComponent app-registry "FunanMobile" (fn [] root-component-factory)))

(comment
  (println RNFastImage))

