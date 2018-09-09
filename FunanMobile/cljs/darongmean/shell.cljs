(ns darongmean.shell)


(defmulti update-context (fn [event _ _] event))


;;; Define dynamic method to register with citrus.
;;; Abuse of multimethod?
(defn citrus [& _] :citrus)


(defmulti broadcast! citrus)


(defmulti broadcast-sync! citrus)


;;; -------------- Subscriptions
;;; Use multimethod because I couldn't find a way to pass reconciler object
;;; from React Native Navigation to rum component.
;;; Maybe RNN v2 will be better?
(defmulti hello-world citrus)