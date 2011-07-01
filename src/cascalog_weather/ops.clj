(ns cascalog-weather.ops
  (:use cascalog.api)
  (:require [cascalog [ops :as c]]))

(defmapcatop duplicate-data
  [window t data]
  (for [dt (range window)]
    [(+ t dt) data]))

(defn moving-average
  [window data]
  (<- [?t2 ?avg]
      (data ?t ?d)
      (duplicate-data window ?t ?d :> ?t2 ?d2)
      (c/count :> window)
      (c/avg ?d2 :> ?avg)))
