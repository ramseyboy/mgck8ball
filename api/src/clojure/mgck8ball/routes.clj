(ns mgck8ball.routes
  (:gen-class)
  (:require [mgck8ball.views :as views]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]]
            [ring.middleware.params :refer [wrap-params]]))

(defroutes app-routes
           (GET "/" [] (views/root))
           (GET "/qa" request (views/qa request))
           (route/not-found "Not Found"))

(def app
  (wrap-params (wrap-json-response app-routes)))

(defn -main
  "entry point"
  []
  (jetty/run-jetty app {:port 3000}))
