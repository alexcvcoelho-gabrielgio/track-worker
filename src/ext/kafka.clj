(ns ext.kafka
  (:require [clojure.data.json :as json]
            [kinsky.client :as client]
            [clojure.core.async :as async]
            [environ.core :refer [env]]
            [mount.core :as mount]))

(mount/defstate conn
                :start (let [c (client/consumer {:bootstrap.servers (env :kafka)
                                                 :group.id          "consumer-tracker-3"}
                                                (client/keyword-deserializer)
                                                (client/edn-deserializer))]
                         (client/subscribe! c "track")
                         c)
                :stop (client/close! conn))

(defn filter-result [r]
  (if (nil? r)
    nil
    (let [v (get (:by-topic r) "track")]
      (map #(:value %) v))))

(defn pop-track-async [ch]
  (async/go
    (async/>!! ch (filter-result (client/poll! conn 1000)))))