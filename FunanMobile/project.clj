;; TODO: sync dependencies with shadow-cljs.edn

(defproject FunanMobile "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["cljs" "rum-native"]
  :dependencies [[expound "0.7.1"]
                 [org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [rum "0.11.2"]
                 [org.roman01la/citrus "3.1.0"]
                 [org.clojure/core.async "0.4.474"]
                 [org.clojure/test.check "0.9.0"]])
