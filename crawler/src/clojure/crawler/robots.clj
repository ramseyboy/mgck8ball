(ns crawler.robots
  (:require [clojure.string :as str]))

(def
  ^{:private true}
  disallowed
  [])

(def
  ^{:private true}
  directives
  #{"user-agent"
    "allow"
    "disallow"
    "crawl-delay"
    "request-rate"
    "robot-version"
    "visit-time"
    "sitemap"})

(defn parse
  "parse robots.txt into map"
  [robots-str]
  (let [lines (str/split-lines robots-str)]
    (let [filtered (filter (fn [x] (not (str/starts-with? x "#"))) lines)]
      (doseq [line filtered]
        (let [splitted (str/split line #":" 2)]
          (let [key (get splitted 0) value (get splitted 1)]
            (cond
              (= key "Disallow")
              (println value)
              )))))))
