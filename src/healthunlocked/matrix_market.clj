(ns healthunlocked.matrix-market
  (:require [clojure.java.io :as io]
            [clojure.core.matrix :as m]
            [clojure.math.combinatorics :as combo]
            [clojure.string :as str])
  (:import [java.util.zip GZIPInputStream GZIPOutputStream]))

(defn- comment?
  [line]
  (str/starts-with? line "%"))

(defn- split-tokens
  [s]
  (str/split s #"\s+"))

(defn- tokenize
  [rdr]
  (->> (line-seq rdr)
       (remove comment?)
       (map split-tokens)))

(defn- non-zero-indices
  "For some reason core.matrix/non-zero-indices actually gives
   you back an N-1 dimensional nested structure with the non-zero
   indices in final dimension. It's a bit awkward to convert it
   into what I actually want, which is a seq of non-zero indices,
   but this seems to do it."
  [m]
  (let [shape (m/shape m)
        idxs  (m/non-zero-indices m)]
    (->> (map range (butlast shape))
         (apply combo/cartesian-product)
         (mapcat #(mapv (partial conj (vec %)) (get-in idxs %))))))

(defn read-matrix
  "Loads a file in gzipped MatrixMarket format into a core.matrix sparse array."
  [filename]
  (with-open [rdr (io/reader (GZIPInputStream. (io/input-stream filename)))]
    (let [[metadata & data] (tokenize rdr)
          shape             (mapv bigint (butlast metadata))
          m                 (m/new-sparse-array shape)]
      (doseq [row data]
        (let [idx    (mapv bigint (butlast row))
              v      (Double/parseDouble (last row))]
          (apply m/mset! m (conj idx v))))
      m)))

(defn write-matrix
  "Writes a sparse matrix to the specified file in MatrixMarket format."
  [m filename & comments]
  (io/delete-file (io/file filename) true)
  (with-open [wtr (io/writer (GZIPOutputStream. (io/output-stream filename)))]
    (binding [*out* wtr]
      (println "%% MatrixMarket matrix")
      (doseq [c comments]
        (println "%" c))
      (println "%")
      (println (str/join " " (m/shape m)) (m/non-zero-count m))
      (doseq [idx (non-zero-indices m)]
        (println (str/join " " idx) (apply m/mget m idx))))))
