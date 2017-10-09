(ns ext.mongo
  (:require [monger.core :as mg]
            [environ.core :refer [env]]
            [monger.collection :as mc])
  (:import org.bson.types.ObjectId))

(mount.core/defstate m-conn
                     :start (mg/connect-via-uri (env :mongo))

                     :stop (mg/disconnect (:conn m-conn)))

(defn get-session [id]
  (mc/find-one-as-map (:db m-conn) "session" {:uuid id}))

(defn save-track [item]
  (let [se (get-session (:session-id item))]
    (if (not (nil? (:_id se)))
      (mc/update-by-id (:db m-conn) "session" (:_id se) (merge se (dissoc item :command :session-id))))))
