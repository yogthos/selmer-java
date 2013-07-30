(ns selmer-java.core
  (:require [selmer.parser :as parser])
  (:gen-class
    :name org.yogthos.Selmer
    :methods [#^{:static true} [render [String java.util.Map] String]
              #^{:static true} [renderFile [String java.util.Map] String]]))

(defn to-map [m]
  (->> (for [e m] [(keyword (.getKey e)) (.getValue e)]) (into {})))

(defn -render [^String template ^java.util.Map context]
  (parser/render template (to-map context)))

(defn -renderFile [^String template ^java.util.Map context]
  (parser/render-file template (to-map context)))
