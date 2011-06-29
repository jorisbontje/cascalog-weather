(ns cascalog-weather.test.weather
  (:use [cascalog.api]
        [cascalog.testing]
        [cascalog-weather.weather]
        [clojure.test]))

(deftest parse-line-should-parse-line-with-commas
  (is (= [:data [1 2 3]] (parse-line "1, 2, 3"))))

(deftest parse-line-should-skip-empty-line
  (is (= [:empty] (parse-line ""))))

(deftest parse-line-should-skip-comments
  (is (= [:comment] (parse-line "# This is a line with comments"))))

(deftest test-parse-line-op
  (with-tmp-sources [lines [["# comment"] [""] ["1, 2, 3"]]]
    (test?<- [[:comment] [:empty] [:data [1 2 3]]]
             [?output]
             (lines ?line)
             (parse-line-op ?line :> ?output))))

(deftest test-parse-line-op
  (with-tmp-sources [lines [["# comment"] [""] ["1, 2, 3"]]]
    (test?<- [[:comment] [:empty] [:data [1 2 3]]]
             [?output]
             (lines ?line)
             (parse-line-op ?line :> ?output))))
