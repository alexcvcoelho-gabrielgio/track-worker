(ns ext.mongo
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import org.bson.types.ObjectId))

(def dev-db-uri "mongodb://remote:remote@gabrielgio.com.br:27017/main")

(defn save-track [conn se]
  (mc/insert (:db conn) "track" (assoc se :_id (ObjectId.))))
