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

(defn not-blank? [s]
  (not (string/blank? s)))

(defn weather-data [source]
  (<- [?station ?date ?valid-temperature]
    (source ?line)
    (is-data-line? ?line)
    (parse-line ?line :#> 24 {0 ?station 1 ?date 7 ?temperature})
    (not-blank? ?temperature)
    (to-pos-long ?temperature :> ?valid-temperature)
    (:distinct false)))

(defn average-temperature-per-month [weather]
  (<- [?station ?yearmonth ?rounded-avg-temperature]
    (weather ?station ?date ?temperature)
    (subs ?date 0 6 :> ?yearmonth)
    (c/avg ?temperature :> ?avg-temperature)
    (format "%.2f" ?avg-temperature :> ?rounded-avg-temperature)
  ))

(defn -main [weather-dir output-dir]
  (let [weather-tap (hfs-textline weather-dir)
        weather (weather-data weather-tap)
        output-tap (hfs-textline output-dir)]
    (?- output-tap (average-temperature-per-month weather))))
