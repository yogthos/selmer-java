(defproject selmer-java "0.94"
  :min-lein-version  "2.0.0"
  :description "Java wrapper for Selmer"
  :url "https://github.com/yogthos/selmer-java"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [selmer "1.10.0"]]
  :source-paths      ["src/clj"]
  :java-source-paths ["src/java"]
  :aot  [selmer-java.core])
