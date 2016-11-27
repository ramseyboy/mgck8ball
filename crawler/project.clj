(defproject mgck8ball "0.1.0-SNAPSHOT"
  :description "A web crawler implementation for indexing html in lucene"
  :url "https://github.com/ramseyboy/"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}
  :source-paths      ["src/clojure"]
  :java-source-paths ["src/java"]
  :javac-options     ["-target" "1.8" "-source" "1.8"]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.squareup.okhttp3/okhttp "3.4.2"]
                 [org.apache.lucene/lucene-core "6.3.0"]
                 [org.apache.lucene/lucene-analyzers-common "6.3.0"]
                 [org.apache.tika/tika-parsers "1.14"]]
  :main ^:skip-aot crawler.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
