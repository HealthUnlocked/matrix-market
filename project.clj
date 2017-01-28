(defproject healthunlocked/matrix-market "0.1.0-SNAPSHOT"
  :description "MatrixMarket reader/writer for Clojure"
  :url "http://github.com/healthunlocked/matrix-market"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/math.combinatorics "0.1.4"]]
  :profiles {:dev {:resource-paths ["dev/resources"]
                   :dependencies [[com.gfredericks/test.chuck "0.2.7"]
                                  [net.mikera/vectorz-clj "0.45.0"]
                                  [org.clojure/test.check "0.9.0"]]
                   :plugins [[lein-ancient "0.6.10"]]}
             :provided {:dependencies [[net.mikera/core.matrix "0.57.0"]]}})
