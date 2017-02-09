(ns lumapi.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [lumapi.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[lumapi started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[lumapi has shut down successfully]=-"))
   :middleware wrap-dev})
