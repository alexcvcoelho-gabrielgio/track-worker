(ns ext.mongo
  (:require [monger.core :as mg]
            [monger.collection :as mc]))

(defn save-track [conn se]
  (let [db (mg/get-db conn "main")]
    (mc/insert db "track" se)))