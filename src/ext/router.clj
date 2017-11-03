(ns ext.router
  (:require [datomic.api :as d]
            [monger.core :as mg]
            [ext.datomic :as db]
            [ext.mongo :as mo]))

(defn save-track [item]
  (mo/save-track item)
  (db/save-track item))

(defn setup []
  (db/transact-schema))
