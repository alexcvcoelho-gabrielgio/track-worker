(ns ext.router
  (:require [datomic.api :as d]
            [monger.core :as mg]
            [ext.datomic :as db]
            [ext.mongo :as mo]))

(defn save-track [item]
  (let [datomic-conn (d/connect db/dev-db-uri)
        mongo-conn (mg/connect-via-uri mo/dev-db-uri)]
    (db/save-track datomic-conn item)
    (mo/save-track mongo-conn item)
    (mg/disconnect (:conn mongo-conn))))


(defn setup []
  (let [conn (d/connect db/dev-db-uri)]
    (db/transact-schema conn)))
