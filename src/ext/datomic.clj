(ns ext.datomic
  (:require [clojure.java.io :as io]
            [datomic.api :as d]
            [environ.core :refer [env]]
            [mount.core :as mount])
  (:import (datomic Util)))

(mount/defstate d-conn
                :start (-> env :datomic d/connect)
                :stop (-> d-conn .release))

(mount/defstate db
                :start (d/db d-conn))


(defn mount-track
  ([{:keys [vel gas-lvl lat long session-id]}]
   [{:session/uid      session-id
     :session/vel      vel
     :session/gas-lvl  gas-lvl
     :session/location #{{:location/x long
                          :location/y lat}}}])
  ([{:keys [vel gas-lvl lat long session-id]} id]
   [{:db/id            id
     :session/uid      session-id
     :session/vel      vel
     :session/gas-lvl  gas-lvl
     :session/location #{{:location/x long
                          :location/y lat}}}]))

(defn save-track [se]
  (try
    (let [e (d/entity db [:session/uid (:session-id se)])]
      (if (not (nil? e))
        (d/transact d-conn (mount-track se (:db/id e)))
        (d/transact d-conn (mount-track se))))
    (catch Exception e
      (println e))))

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

(def schema (io/resource "schema.edn"))

(defn transact-schema []
  (transact-all d-conn (read-txs schema)))