(ns user
  (:require [mount.core :as mount]
            [lumapi.figwheel :refer [start-fw stop-fw cljs]]
            lumapi.core))

(defn start []
  (mount/start-without #'lumapi.core/http-server
                       #'lumapi.core/repl-server))

(defn stop []
  (mount/stop-except #'lumapi.core/http-server
                     #'lumapi.core/repl-server))

(defn restart []
  (stop)
  (start))


