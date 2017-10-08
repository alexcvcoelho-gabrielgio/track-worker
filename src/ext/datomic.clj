(ns ext.datomic
  (:require [clojure.java.io :as io]
            [datomic.api :as d]
            [environ.core :refer [env]]
            [mount.core :as mount])
  (:import (datomic Util)))

(mount/defstate d-conn
                :start (-> env :datomic d/connect)
                :stop (-> d-conn .release))

(defn get-track [{:keys [vel gas-lvl lat long session-id]}]
  [{:track/vel        vel
    :track/gas-lvl    gas-lvl
    :track/lat        lat
    :track/session-id session-id
    :track/long       long}])

(def schema (io/resource "schema.edn"))

(defn save-track [se]
  ((try
     (d/transact d-conn (get-track se))
     (catch Exception e
       (println e)))))

(defn read-txs
  [tx-resource]
  (with-open [tf (io/reader tx-resource)]
    (Util/readAll tf)))

(defn transact-all
  ([conn txs]
   (transact-all conn txs nil))
  ([conn txs res]
   (if (seq txs)
     (transact-all conn (rest txs) @(d/transact conn (first txs)))
     res)))

(defn transact-schema []
  (transact-all d-conn (read-txs schema)))