(defproject mgck8ball "0.1.0-SNAPSHOT"
  :description "mgck8ball client app"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.293"]
                 [reagent "0.6.0"]]
  :plugins [[lein-cljsbuild "1.1.4"]]
  :cljsbuild {:builds [{:source-paths ["src-cljs"]
                        :compiler     {:output-to     "resources/public/js/app.js"
                                       :optimizations :whitespace
                                       :preamble      ["reagent/react.js"]
                                       :pretty-print  true}}]})
