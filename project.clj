(defproject track-worker "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories {"my.datomic.com" {:url      "https://my.datomic.com/repo"
                                   :username "gabrielg.desouza@gmail.com" :password ""}}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.3.442"]
                 [commons-codec "1.10"]
                 [cprop "0.1.10"]
                 [mysql/mysql-connector-java "5.1.6"]
                 [com.datomic/datomic-pro "0.9.5561"]
                 [com.taoensso/carmine "2.16.0"]
                 [org.clojure/data.json "0.2.6"]
                 [com.novemberain/monger "3.1.0"]
                 [cprop "0.1.10"]]
  :main ^:skip-aot track-worker.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
