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
           (json/read-str body :key-fn keyword)))

(defn exits [room-id]
      (:exits (request "/exits" {"roomId" room-id})))

(defn wall [room-id]
      (request "/wall" {"roomId" room-id}))

(defn move [room-id exit]
      (request "/move" {"roomId" room-id "exit" exit}))

(def start
      (request "/start" {}))

(defn find-black-rooms [room-id]
  (loop [new-exits (exits room-id)
        walls []]
       (println "Exits; " new-exits)
       (if (empty? new-exits)
         walls
         (recur (rest new-exits)
                (conj walls (find-black-rooms (move room-id (first new-exits)))))))
  )

(defn -main
      [& args]
      (println "Results " (:roomId start))
      (println "Move " (move (:roomId start) "south"))
      (find-black-rooms (:rommId start)))