(defproject cascalog-weather "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :jvm-opts ["-Xmx768m"]
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [cascalog/cascalog "1.7.1"]]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-cdh3u0"]]
  :main cascalog-weather.weather
  :repositories {"cloudera-releases" "https://repository.cloudera.com/content/repositories/releases/"})
