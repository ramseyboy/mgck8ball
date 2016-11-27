(ns crawler.core
  (:gen-class)
  (:import [okhttp3 OkHttpClient]
           (okhttp3 OkHttpClient$Builder Request$Builder)
           (crawler HtmlProcessor)))

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

(defn -main
  "entry point"
  []
  (let [req (build-request "http://www.usatoday.com/")]
    (let [resp (make-request req)]
      (process-html (.url req) resp))))