(ns cascalog-weather.test.ops
  (:use [cascalog.api]
        [cascalog.testing]
        [cascalog-weather.ops]
        [clojure.test]))

(deftest test-moving-average
  (with-tmp-sources [values [[0 5] [1 2] [2 5] [3 2] [4 5] [5 8]]]
                    (test?- :warn [[2 4] [3 3] [4 4] [5 5]]
                            (moving-average 3 values))))

