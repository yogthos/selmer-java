(ns selmer-java.core
  (:require [selmer.parser :as parser]
            [selmer.tags :refer [tag-handler]]
            [selmer.filters :as filters])
  (:import [selmer.extensions Filter])
  (:gen-class
    :name org.yogthos.Selmer
    :methods [#^{:static true} [render [String java.util.Map] String]
              #^{:static true} [renderFile [String java.util.Map] String]
              #^{:static true} [addFilter [String selmer.extensions.Filter] void]
              #^{:static true} [addTag [String selmer.extensions.Tag] void]
              ]))

(declare clojurize javaize)

(defn to-map [m]
  (->> (for [e m]
         [(keyword (.getKey e)) (clojurize (.getValue e))])
       (into {})))

(defn clojurize [v]
  (cond
    (instance? java.util.Collection v)
    (map clojurize v)
    (instance? java.util.Map v)
    (to-map v)
    :else v))

(defn java-list [coll]
  (let [l (java.util.ArrayList.)]
    (doseq [item coll] (.add l item))
    (javaize l)))

(defn java-map [m]
  (let [hm (java.util.HashMap.)]
    (doseq [[k v] m] (.put hm (name k) (javaize v)))
    hm))

(defn javaize [v]
  (cond
   (map? v)
   (java-map v)
   (coll? v)
   (java-list v)
   :else v))

(defn -addFilter [^String k ^selmer.extensions.Filter f]
  (filters/add-filter! (keyword k) #(.render f %))
  nil)

(defn -addTag [^String tagName ^selmer.extensions.Tag t]
  (let [open-tag (keyword tagName)]
    (swap! parser/expr-tags
      assoc open-tag
           (tag-handler #(.render t (javaize %1) (javaize %2)) open-tag))
    nil))

(defn -render [^String template ^java.util.Map context]
  (parser/render template (to-map context)))

(defn -renderFile [^String template ^java.util.Map context]
  (parser/render-file template (to-map context)))
