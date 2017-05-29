(ns ext.datomic
  (:require [cprop.core :refer [load-config]]
            [clojure.java.io :as io]
            [datomic.api :as d]
            [cprop.source :as source])
  (:import (datomic Util)))

(def dev-db-uri "datomic:sql://main?jdbc:mysql://gabrielgio.com.br:3306/datomic?user=remote&password=remote")

(defn get-track [{:keys [vel gas-lvl lat long session-id]}]
  [{:track/vel        vel
    :track/gas-lvl    gas-lvl
    :track/lat        lat
    :track/session-id session-id
    :track/long       long}])

(def schema (io/resource "schema.edn"))

(defn save-track [conn se]
  (d/transact conn (get-track se)))

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

(defn transact-schema [conn]
  (transact-all conn (read-txs schema)))