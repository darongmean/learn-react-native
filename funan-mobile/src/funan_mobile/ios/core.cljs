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

(defc AppRoot < rum/reactive [state]
  (view {:style {:flexDirection "column" :margin 40 :alignItems "center"}}
        (text {:style {:fontSize 30 :fontWeight "100" :marginBottom 20 :textAlign "center"}}
              (:greeting (rum/react state)))
        (image {:source logo-img
                :style  {:width 80 :height 80 :marginBottom 30}})
        (touchable-highlight {:style   {:backgroundColor "#999" :padding 10 :borderRadius 5}
                              :onPress #(alert "HELLO !!")}
                             (text {:style {:color "white" :textAlign "center" :fontWeight "bold"}} "press me"))))

(defonce root-component-factory (support/make-root-component-factory))

(defn mount-app [] (support/mount (AppRoot app-state)))

(defn init []
  (mount-app)
  (.registerComponent app-registry "FunanMobile" (fn [] root-component-factory)))
