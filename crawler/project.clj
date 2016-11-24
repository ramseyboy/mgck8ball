(defproject mgck8ball "0.1.0-SNAPSHOT"
  :description "A web crawler implementation for indexing html in lucene"
  :url "https://github.com/ramseyboy/"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot crawler.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
