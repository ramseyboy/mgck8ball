(ns crawler.core
  (:gen-class)
  (:import [okhttp3 OkHttpClient]
           (okhttp3 OkHttpClient$Builder Request$Builder)
           (crawler HtmlProcessor)
           (okio Okio))
  (:require [clojure.java.io :as io]
            [crawler.robots :as robots]))

(def client (-> (OkHttpClient$Builder.) (.build)))

(defn build-request
  "builds http GET request"
  [input-url]
  (-> (Request$Builder.)
      (.url input-url)
      (.build)))

(defn make-request
  "docstring"
  [req]
  (.execute (-> client (.newCall req))))

(defn process-html
  "docstring"
  [url resp]
  (-> (HtmlProcessor.) (.process url resp)))

(defn crawl
  "crawl given url with provided config"
  []
  (let [req (build-request "http://www.usatoday.com/")]
    (let [resp (make-request req)]
      (process-html (.url req) resp))))

(defn -main
  "entry point"
  []
  (let [robots-file (io/file (io/resource "robots/usa-robots.txt"))]
    (let [robots-str (.readUtf8 (Okio/buffer (Okio/source robots-file)))]
      (println (robots/parse robots-str)))))