(ns labyrinth-app.core
    (:require [org.httpkit.client :as http])
    (:require [clojure.data.json :as json]))

(def host
  "http://challenge2.airtime.com:7182")

(defn options [params]
           {:timeout 200
            :query-params params
            :headers {"X-Labyrinth-Email" "dianagren@gmail.com"}})

(def default-options
  (options ""))

(defn url [host path]
      (clojure.string/join [host path]))

(defn request [path params]
      (let [{:keys [status header body error] :as resp} @(http/get (url host path) (options params))]
           (println "path: " path " status: " status)
           (json/read-str body)))


(defn -main
      [& args]
      (println "Results " (request "/exits" {"roomId" ((request "/start" "") "roomId")})))