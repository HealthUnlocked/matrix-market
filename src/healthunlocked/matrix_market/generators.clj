(ns healthunlocked.matrix-market.generators
  "Generators for use when testing spare matrices"
  (:require [clojure.core.matrix :as m]
            [clojure.test.check.generators :as gen]))

(defn index
  [shape]
  (apply gen/tuple
         (map #(gen/choose 0 (dec %)) shape)))

(defn value
  [shape]
  (gen/tuple (index shape) (gen/double* {:infinite? false :NaN? false})))

(defn matrix
  [& {:keys [ max-dimensions max-values]}]
  (gen/let [shape  (gen/vector gen/s-pos-int 1 max-dimensions)
            values (gen/vector (value shape) 0 max-values)]
    (let [m (m/new-sparse-array shape)]
      (doseq [[index value] values]
        (apply m/mset! m (conj index value)))
      m)))
