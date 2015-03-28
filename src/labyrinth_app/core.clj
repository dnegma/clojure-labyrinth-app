(ns labyrinth-app.core
    (:require [org.httpkit.client :as http])
    (:require [clojure.data.json :as json]))

(def options {:timeout 200
              :headers {"X-Labyrinth-Email" "dianagren@gmail.com"}})
(def airtime-host
  "http://challenge2.airtime.com:7182")

(defn url [host path]
      (clojure.string/join [host path]))

(defn request [path]
      (let [{:keys [status header body error] :as resp} @(http/get (url airtime-host path) options)]
           (if error
             (println "Error " error)
             (println "Success " status " results: " body "room id: " ((json/read-str body) "roomId")))))
(defn -main
      [& args]
      (request "/start"))