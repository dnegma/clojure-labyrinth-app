(ns labyrinth-app.core-test
  (:require [clojure.test :refer :all]
            [labyrinth-app.core :refer :all]))

(deftest url-test
         (is (= "www.google.com/test" (url "www.google.com" "/test"))))