(ns labyrinth-app.core
    (:require [org.httpkit.client :as http]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(def options {:timeout 200
              :headers {"X-Labyrinth-Email" "dianagren@gmail.com"}})
(defn -main
      [& args]
      (let [{:keys [status header body error] :as resp} @(http/get "http://challenge2.airtime.com:7182/start" options)]
           (if error
             (println "Error " error)
             (println "Success " status " results: " body))))