(ns mgck8ball.views
  (:use [korma.db]
        [korma.core])
  (:require [mgck8ball.qa :refer [retreive-answer]]
            [ring.util.response :refer [response]]
            [mgck8ball.migration :as migration]))

(defn root
  "handler for root api"
  []
  (response {:routes
             {:root "/"
              :ask  "/ask"}}))

(defn ask
  "ask a question and you will receive, a bad answer"
  [request]
  (let [question? (get (:params request) "question") answer (retreive-answer question?)]
    (let [resp-map {:question question? :answer answer}]
      (insert migration/qa (values resp-map))
      (response resp-map))))

(defn answers
  "returns all answers"
  []
  (select migration/qa
          (fields :answer)
          (modifier "DISTINCT")))

(defn questions
  "returns all asked questions"
  []
  (select migration/qa
          (fields :question)
          (modifier "DISTINCT")))
