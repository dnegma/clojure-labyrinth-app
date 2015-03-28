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
  (let [{:keys [body]} @(http/get (url path) (options params))]
    (json/read-str body :key-fn keyword)))

(defn exits [room-id]
  (:exits (request "/exits" {"roomId" room-id})))

(defn wall [room-id]
  (request "/wall" {"roomId" room-id}))

(defn move [room-id exit]
  (:roomId (request "/move" {"roomId" room-id "exit" exit})))

(def start
  (request "/start" {}))

(defn walk-all-rooms [room-id]
  (loop [exits (exits room-id)
         walls []]
    (if (empty? exits)
      (conj walls (assoc (wall room-id) :roomId room-id))
      (recur (rest exits)
             (concat walls (walk-all-rooms (move room-id (first exits))))))))

(defn is-dark? [room]
  (and (= (:writing room) "xx") (= (:order room) -1)))

(defn compare-rooms [room-a room-b]
  (> (:order room-b) (:order room-a)))

(defn create-report [rooms]
  (let [dark-rooms (filter is-dark? rooms)
        light-rooms (remove is-dark? rooms)]
    {:roomIds   (map :roomId dark-rooms)
     :challenge (clojure.string/join (map :writing (sort compare-rooms light-rooms)))}))

(defn -main
  [& args]
  (let [all-rooms (walk-all-rooms (:roomId start))]
    (println (create-report all-rooms))))