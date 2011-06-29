(ns cascalog-weather.weather
  (:use cascalog.api)
  (:require [cascalog [vars :as v] [ops :as c]]
            [clojure.string :as string])
  (:gen-class))

(defn to-long [num] (Long/parseLong (string/trim num)))

(defn parse-line [line]
  (cond
    (.isEmpty line) [:empty]
    (.startsWith line "#") [:comment]
    :else [:data (map to-long (string/split line #","))]))

(defmapop parse-line-op [& line]
  (parse-line line))

(defn -main [weather-dir output-dir]
  (println "Input and output:" weather-dir output-dir))

