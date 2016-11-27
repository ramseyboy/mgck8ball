(ns crawler.robots
  (:require [clojure.string :as str]))

(defrecord
  UserAgent [useragent disallows])
(defrecord
  RobotDirectives [useragents sitemaps])

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

(defn- lines
  "parse string into lines, applying filters"
  [robots-str]
  (map (fn [line] (str/lower-case line))
         (filter (fn [x] (not (str/starts-with? x "#")))
                 (filter (fn [x] (not (str/blank? x)))
                         (str/split-lines robots-str)))))

(defn- split
  "split a line into a key-value pair"
  [line]
  (let [splitted (str/split line #":" 2)]
    (let [key (str/trim (get splitted 0))
          value (str/trim (get splitted 1))]
      [key value])))

(defn- filter-directives
  "filter out lines that do not start with the specified directive,
  then return a new seq with only the values for that directive"
  [lines directive]
  (map
    (fn [line]
      (let [[key value] (split line)] value))
    (filter
      (fn [line]
        (str/starts-with? line directive)) lines)
    )
  )

(defn- map-useragents
  "return the disallows for each user agent"
  [lines useragents]
  (let [disallows (filter-directives lines "disallow")]
    (map (fn [useragent] (UserAgent. useragent disallows)) useragents)))

(defn parse
  "parse robots.txt into record"
  [robots-str]
  (let [lines (lines robots-str)]
    (let [useragents (map-useragents lines (filter-directives lines "user-agent"))
          sitemaps (filter-directives lines "sitemap")]
      (RobotDirectives. useragents sitemaps)))
  )
