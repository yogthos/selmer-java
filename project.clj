(defproject selmer-java "0.1"
  :description "A Java wrapper for Selmer"
  :url "https://github.com/yogthos/selmer-java"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [selmer "0.2.4"]]
  :aot  [selmer-java.core])
