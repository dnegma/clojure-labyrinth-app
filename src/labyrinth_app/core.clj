(ns labyrinth-app.core
    (:require [org.httpkit.client :as http])
    (:require [clojure.data.json :as json]))

(defn options [params]
           {:timeout 200
            :query-params params
            :headers {"X-Labyrinth-Email" "dianagren@gmail.com"}})

(defn url [path]
      (clojure.string/join ["http://challenge2.airtime.com:7182" path]))

(defn request [path params]
      (let [{:keys [status header body error] :as resp} @(http/get (url path) (options params))]
           ;(println "path: " path " status: " status "params" params)
           (json/read-str body :key-fn keyword)))

(defn exits [room-id]
      (:exits (request "/exits" {"roomId" room-id})))

(defn wall [room-id]
      (request "/wall" {"roomId" room-id}))

(defn move [room-id exit]
      (:roomId (request "/move" {"roomId" room-id "exit" exit})))

(def start
      (request "/start" {}))

(defn find-black-rooms [room-id]
  (loop [new-exits (exits room-id)
        walls []]
       ;(println "Exits; " new-exits "room-id" room-id)
       (if (empty? new-exits)
         (conj walls (wall room-id))
         (recur (rest new-exits)
                (conj walls (find-black-rooms (move room-id (first new-exits)))))))
  )

(defn -main
      [& args]
      ;(println "Results " (:roomId start))
      ;(println "Move " (move (:roomId start) "south"))
      (println (find-black-rooms (:roomId start))))