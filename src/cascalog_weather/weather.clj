(ns cascalog-weather.weather
  (:use cascalog.api)
  (:require [cascalog [vars :as v] [ops :as c]]
            [clojure.string :as string])
  (:gen-class))

(defn to-long [num] (Long/parseLong num))

(defn is-data-line? [line]
  (not (or
    (.isEmpty line)
    (.startsWith line "#"))))

(defn parse-line [line]
  (map string/trim (string/split line #",")))

(defn to-pos-long [p]
  (long (max 0 (to-long p))))

(defn weather-data [source]
  (<- [?station ?date ?valid-precipitation]
    (source ?line)
    (is-data-line? ?line)
    (parse-line ?line :#> 41 {0 ?station 1 ?date 22 ?precipitation})
    (to-pos-long ?precipitation :> ?valid-precipitation)
    (:distinct false)))

(defn average-precipitation-per-month [weather]
  (<- [?station ?yearmonth ?rounded-avg-precipitation]
    (weather ?station ?date ?precipitation)
    (subs ?date 0 6 :> ?yearmonth)
    (c/avg ?precipitation :> ?avg-precipitation)
    (format "%.2f" ?avg-precipitation :> ?rounded-avg-precipitation)
  ))

(defn -main [weather-dir output-dir]
  (let [weather-tap (hfs-textline weather-dir)
        weather (weather-data weather-tap)
        output-tap (hfs-textline output-dir)]
    (?- output-tap (average-precipitation-per-month weather))))
