(defproject selmer-java "0.9"
  :min-lein-version  "2.0.0"
  :description "FIXME: write description"
  :url "https://github.com/yogthos/selmer-java"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [selmer "0.5.3"]]
  :source-paths      ["src/clj"]
  :java-source-paths ["src/java"]
  :aot  [selmer-java.core])
