(defproject selmer-java "0.5"
  :min-lein-version  "2.0.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [selmer "0.3.6"]]
  :source-paths      ["src/clj"]
  :java-source-paths ["src/java"]
  :aot  [selmer-java.core])
