(ns cascalog-weather.test.weather
  (:use [cascalog.api]
        [cascalog.testing]
        [cascalog-weather.weather]
        [clojure.test]))

(deftest is-data-line-should-skip-empty-line
  (is (not (is-data-line? ""))))

(deftest is-data-line-should-skip-comments
  (is (not (is-data-line? "# This is a line with comments"))))

(deftest is-data-line-should-pass-data
  (is (is-data-line? "1, 2, 3")))

(deftest parse-line-should-parse-line-with-commas
  (is (= ["1" "2" "3"] (parse-line "1, 2, 3"))))

(deftest test-weather-data
  (with-tmp-sources [lines [["#comment"] [""]
[" 240,20110701,    1,  300,   30,   30,   60,  130,     ,  101,    0,    0,    0,   -1,10231,   83,    6,   83,   91,    0,    1,    0,    1,    0"]
[" 240,20110702,    1,  310,   20,   20,   30,  117,     ,   88,    0,    0,    0,    0,10217,   81,    8,   82,     ,    0,    0,    0,    0,    0"]]]
    (test?- :warn [["240" "20110701" 130] ["240" "20110702" 117]]
            (weather-data lines))))

(deftest test-average-temperature-per-month
  (with-tmp-sources [data [["240" "20110701" 0] ["240" "20110702" 7]]]
    (test?- :warn [["240" "201107" "3.50"]]
            (average-temperature-per-month data))))
