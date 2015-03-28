(ns labyrinth-app.core-test
  (:require [clojure.test :refer :all]
            [labyrinth-app.core :refer :all]))

(def rooms
  [{:roomId "light" :writing "c" :order 3}
   {:roomId "dark" :writing "xx" :order -1}
   {:roomId "light" :writing "a" :order 1}
   {:roomId "light" :writing "b" :order 2}
   {:roomId "dark" :writing "xx" :order -1}])

(deftest create-report-test
  (is (= (create-report rooms) {:roomIds ["dark" "dark"] :challenge "abc"})))