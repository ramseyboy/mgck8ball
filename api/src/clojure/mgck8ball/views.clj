(ns mgck8ball.views
  (:use [mgck8ball.qa])
  (:require [ring.util.response :refer [response]]))

(defn root
  "handler for root api"
  []
  (response {:routes
             {:root   "/"
              :qa "/qa"}}))

(defn qa
  "handler for root api"
  [request]
  (let [question? (get (:params request) "question")]
    (response (retreive-answer question?))))
