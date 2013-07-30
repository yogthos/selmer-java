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
              #^{:static true} [addTag ["[Ljava.lang.String;" selmer.extensions.Tag] void]
              ]))

(declare clojurize)

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

(defn -addFilter [^String k ^selmer.extensions.Filter f]
  (filters/add-filter! (keyword k) #(.render f %))
  nil)

(defn tag-handler-fn [^selmer.extensions.Tag t]
  (fn [args context-map]
    (.render t args context-map)))

(defn -addTag [^"[Ljava.lang.String;" tagNames ^selmer.extensions.Tag t]
  (let [[open-tag & closing-tags] (map keyword tagNames)]
    (swap! parser/expr-tags
           #(assoc % open-tag
              (apply (partial tag-handler open-tag (tag-handler-fn t)) closing-tags)))
    nil))

(defn -render [^String template ^java.util.Map context]
  (parser/render template (to-map context)))

(defn -renderFile [^String template ^java.util.Map context]
  (parser/render-file template (to-map context)))
