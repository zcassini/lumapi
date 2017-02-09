(ns lumapi.routes.home
  (:require [lumapi.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [ring.util.response :refer [redirect]]
            [clojure.java.io :as io]
            [clj-time.coerce]
            [clj-time.format]
            [tupelo.y64 :as y64]
            [clj-dns.core :as dns]
            [lumapi.db.core :as db]))
;; (import 'org.xbill.DNS.Type)
;; (import java.net InetAddress)
(import 'org.apache.commons.validator.UrlValidator)

(defn valid-url? [url-str]
  (let [validator (UrlValidator.)]
    (.isValid validator url-str)))

(defn home-page []
  (layout/render "home.html"))

(defn timestamp [date]
  (let [ymd (clj-time.format/formatter :year-month-day)
        utc (clj-time.format/formatter :rfc822)
        respond (fn [date]
                  {:status 200
                   :headers {"Content-Type" "application/json"}
                   :body {:unix (clj-time.coerce/to-long date)
                          :utc (clj-time.format/unparse utc date)}})]
    (cond
      (re-find #"^\d+$" date) (respond (clj-time.coerce/from-long (read-string date)))
      (re-find #"^\d{4}-\d{1,2}-\d{1,2}$" date) (respond (clj-time.format/parse ymd date))
      :else {:status 200 :headers {"Content-Type" "application/json"} :body {:error "Invalid Date"}})))

(defn whoami [req]
  (let [ipaddress (or (get-in req [:headers "x-forwarded-for"]) (:remote-addr req))
        software (get-in req [:headers "user-agent"])
        language (get-in req [:headers "accept-language"])]
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body {:ipaddress ipaddress :language language :software software}}))

(defn short-get-url [url-id]
  (println url-id "-------------------------------------------------------")
  (let [lookup (db/get-url {:id url-id})]
    (println lookup)
    (if (nil? lookup) {:status 200
       :headers {"Content-Type" "text/plain"}
       :body (str "The shortened url: " url-id " does not exist")}
      (redirect (str "http://" (:url lookup))))))

(defn new-url [url]
  (if (valid-url? url)
    (db/add-url! {:url url :timestamp (java.util.Date.)})
    ( {:status 200
       :headers {"Content-Type" "text/plain"}
       :body (str "The url: " url " is not valid")})))

(defroutes home-routes
  (GET "/" []
       (home-page))
  (POST "/hello" [id url]
        (println "hihihi")
        (println id)
        (str "Welcome "  url id))
  (GET "/docs" []
       (-> (response/ok (-> "docs/docs.md" io/resource slurp))
           (response/header "Content-Type" "text/plain; charset=utf-8")))
  (GET "/api/timestamp/:date" [date]
       (timestamp date))
  (GET "/api/whoami" [] whoami)
  ;; (POST "/api/shorturl/new/:url" [url]
  ;;       ;; (new-url url))
  ;;       (home-page))
  (POST "/api/shorturl/new" [url]
        (new-url url))
  ;; (GET "/api/shorturl/:url-id" [url-id]
  ;;      (short-get-url url-id)))
 
