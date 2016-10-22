(ns mgck8ball.qa)

(def answers (list "Ask again.." "No" "Yes" "I dont know" "You wish" "Ask your mother"))

(defn answer
  "Prints answer to stdout"
  [question?]
  (rand-nth answers))

(defn retreive-answer
  "get answer"
  [question?]
  (let [answer (answer question?)]
    {:question question? :answer answer}))
