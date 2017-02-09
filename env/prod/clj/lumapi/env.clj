(ns lumapi.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[lumapi started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[lumapi has shut down successfully]=-"))
   :middleware identity})
