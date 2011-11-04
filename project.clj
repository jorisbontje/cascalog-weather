(defproject cascalog-weather "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :jvm-opts ["-Xmx768m"]
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [cascalog/cascalog "1.8.3"]
                 [ch.qos.logback/logback-classic "0.9.30"]]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-cdh3u0"]]
  :main cascalog-weather.weather
  :repositories {"cloudera-releases" "https://repository.cloudera.com/content/repositories/releases/"})
