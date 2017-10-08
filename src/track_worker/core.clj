(ns track-worker.core
  (:gen-class)
  (:require [ext.kafka :as kf]
            [clojure.core.async :refer [go thread chan <!!]]
            [clojure.core.async :refer [go thread]]
            [ext.router :as rt]
            [clojure.data.json :as json]))

(defn pull [item]
  (println (:command item) " " (:session-id item))
  (case (:command item)
    "track" (rt/save-track item)
    nil))

(defn loop-through [c]
  (loop [count 0]
    (try
      (println "LOOP " count)
      (kf/pop-track-async c)
      (doseq [i (<!! c)]
        (pull i))
      (catch Exception e
        (-> e print)))
    (recur (inc count))))

(defn -main [& args]
  (mount.core/start)
  (let [c (chan)]
    (loop-through c)))
