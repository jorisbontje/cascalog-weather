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
  (is (= [1 2 3] (parse-line "1, 2, 3"))))

(deftest test-combined
  (with-tmp-sources [lines [["#comment"] [""] ["1, 2, 3"]]]
    (test?<- [[1 2 3]]
             [?station ?date ?precipitation]
             (lines ?line)
             (is-data-line? ?line)
             (parse-line ?line :> ?station ?date ?precipitation))))
