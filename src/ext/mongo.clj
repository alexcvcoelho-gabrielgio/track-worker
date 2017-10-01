(ns ext.mongo
  (:require [monger.core :as mg]
            [environ.core :refer [env]]
            [monger.collection :as mc])
  (:import org.bson.types.ObjectId))

(mount.core/defstate m-conn
                     :start (mg/connect-via-uri (env :mongo))
                     :stop (mg/disconnect (:conn m-conn)))

(defn save-track [se]
  (mc/insert (:db m-conn) "track" (assoc se :_id (ObjectId.))))
