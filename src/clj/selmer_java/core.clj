(ns selmer-java.core
  (:require [selmer.parser :as parser]
            [selmer.tags :refer [tag-handler]]
            [selmer.filters :as filters]
            selmer.node)
  (:import [selmer.extensions Filter]
           [selmer.node INode TextNode FunctionNode])
  (:gen-class
    :name org.yogthos.Selmer
    :methods [#^{:static true} [render [String java.util.Map] String]
              #^{:static true} [renderFile [String java.util.Map] String]
              #^{:static true} [addFilter [String selmer.extensions.Filter] void]
              #^{:static true} [addTag [String selmer.extensions.Tag] void]
              #^{:static true} [addBlockTag [String String selmer.extensions.BlockTag] void]
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
   (instance? INode) (.render-node v)
   (map? v)          (java-map v)
   (coll? v)         (java-list v)
   :else             v))

(defn -addFilter [^String k ^selmer.extensions.Filter f]
  (filters/add-filter! (keyword k) #(.render f (javaize %)))
  nil)

(defn -addTag [^String tagName ^selmer.extensions.Tag t]
  (let [open-tag (keyword tagName)]
    (swap! parser/expr-tags
      assoc open-tag
           (tag-handler #(.render t (javaize %1) (javaize %2)) open-tag))
    nil))

(defn render-content [content open-tag close-tag]
  (-> content
      (update-in [open-tag :content] #(apply str (map (memfn render-node) %)))
      (update-in [close-tag :content] #(apply str (map (memfn render-node) %)))
      javaize))

(defn render-block [renderer]
  (fn [args context-map content]
    (.render renderer
             (javaize args)
             (javaize context-map)
             (javaize content))))


(defn -addBlockTag [^String openTag ^String closeTag ^selmer.extensions.BlockTag t]
  (let [open-tag (keyword openTag)
        close-tag (keyword closeTag)]
    (swap! parser/expr-tags
           assoc open-tag
           (tag-handler (render-block t) open-tag close-tag))
    nil))

(defn -render [^String template ^java.util.Map context]
  (parser/render template (to-map context)))

(defn -renderFile [^String template ^java.util.Map context]
  (parser/render-file template (to-map context)))
