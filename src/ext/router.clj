(ns ext.router
  (:require [datomic.api :as d]
            [monger.core :as mg]
            [ext.datomic :as db]
            [ext.mongo :as mo]))

(defn save-session [item]
  (let [datomic-conn (d/connect db/dev-db-uri)
        mongo-conn (mg/connect)]
    (db/save-track datomic-conn item)
    (mo/save-track mongo-conn item)))


(defn setup []
  (let [conn (d/connect db/dev-db-uri)]
    (db/transact-schema conn)))
