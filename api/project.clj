(defproject mgck8ball "0.1.0-SNAPSHOT"
  :description "A question/answer api"
  :url "https://github.com/ramseyboy/"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}
  :source-paths      ["src/clojure"]
  :java-source-paths ["src/java"]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-core "1.5.0"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-jetty-adapter "1.5.0"]
                 [compojure "1.5.1"]
                 [korma "0.4.0"]
                 [org.xerial/sqlite-jdbc "3.14.2.1"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler mgck8ball.routes/app}
  :main ^:skip-aot mgck8ball.routes
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
