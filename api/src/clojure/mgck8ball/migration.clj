(ns mgck8ball.migration
  (:use [korma.db]
        [korma.core])
  (:require [clojure.string :as str]))

(defdb db-conn (sqlite3
                 {:db "resources/db/qa.db"
                  :naming {:keys str/lower-case
                           :fields str/upper-case}}))

(defentity qa)