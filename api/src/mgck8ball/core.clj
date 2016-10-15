(ns mgck8ball.core
  (:gen-class)
  (:use [mgck8ball.qa])
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response not-found]]
            [ring.middleware.params :refer [wrap-params]]))

(defn root-handler
  "handler for root api"
  (response {:routes
             {:root   "/"
              :answer "/answer"}}))

(defn answer-handler
  "handler for root api"
  [query-map path]
  (response (retreive-answer (get query-map "question"))))

(def routes {
             "/" root-handler
             "/answer" answer-handler
             })

(defn handler [request]
  (let [query-map (:params request) path (:uri request)]
    (cond
      (re-matches #"/" path) (root-handler)
      (re-matches #"/answer" path) (answer-handler query-map path)
      :else (not-found "No matching routes"))))

(def app
  (wrap-params (wrap-json-response handler)))

(defn -main
  "entry point"
  []
  (jetty/run-jetty app {:port 3000}))
