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
[" 240,20100101,   28,   42,   46,   80,    3,    0,   21,  130,    4,  -16,  -63,   22,    8,   13,  -86,   24,   50,   64,  343,    0,   -1,   -1,    1,10030,10094,   24, 9985,    3,   60,    1,   80,   15,    3,   82,   95,   24,   69,   15,    3"]
[" 240,20100102,  186,   23,   43,   80,   24,   20,    1,  120,   23,   -6,  -55,    1,   16,   16,  -68,    6,    3,    4,  146,   20,    7,    5,   16,10136,10178,   24,10098,    1,   24,   16,   68,    3,    8,   95,   98,    9,   90,    3,    2"]]]
    (test?- [["240" "20100101" 0] ["240" "20100102" 7]]
            (weather-data lines))))

(deftest weather-data-should-skip-lines-without-precipitation
  (with-tmp-sources [lines [["#comment"] [""]
[" 240,20100103,   28,   42,   46,   80,    3,    0,   21,  130,    4,  -16,  -63,   22,    8,   13,  -86,   24,   50,   64,  343,    0,     ,   -1,    1,10030,10094,   24, 9985,    3,   60,    1,   80,   15,    3,   82,   95,   24,   69,   15,    3"]]]
    (test?- []
            (weather-data lines))))


(deftest test-average-precipitation-per-month
  (with-tmp-sources [data [["240" "20100101" 0] ["240" "20100102" 7]]]
    (test?- [["240" "201001" "3.50"]]
            (average-precipitation-per-month data))))
