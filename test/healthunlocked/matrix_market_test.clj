(ns healthunlocked.matrix-market-test
  (:require [clojure.core.matrix :as m]
            [clojure.test :as t :refer [deftest testing is are run-tests]]
            [com.gfredericks.test.chuck.clojure-test :as chuck :refer [checking]]
            [healthunlocked.matrix-market :as mm]
            [healthunlocked.matrix-market.generators :as g]))

(m/set-current-implementation :vectorz)

(deftest read-and-write-matrix-tests
  (checking "read-matrix returns a matrix that matches the one that was originally written"
    (chuck/times 20)
    [m (g/matrix :max-dimensions 5 :max-values 100)]

    (mm/write-matrix m "tmp/test-matrix.mm.gz" "A test matrix in MatrixMarket format" "a comment")

    (is (= m
           (mm/read-matrix "tmp/test-matrix.mm.gz")))))
