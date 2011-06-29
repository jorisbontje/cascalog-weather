(ns cascalog-weather.weather
  (:use cascalog.api)
  (:require [cascalog [vars :as v] [ops :as c]]
            [clojure.string :as string])
  (:gen-class))

(defn to-long [num] (Long/parseLong (string/trim num)))

(defn is-data-line? [line]
  (not (or
    (.isEmpty line)
    (.startsWith line "#"))))

(defn parse-line [line]
  (map to-long (string/split line #",")))

(defn -main [weather-dir output-dir]
  (println "Input and output:" weather-dir output-dir))

