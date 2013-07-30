package selmer.extensions;

public interface Tag {
  public String render(clojure.lang.IPersistentCollection args,
                       clojure.lang.IPersistentMap context);

public String render(clojure.lang.IPersistentCollection args,
                     clojure.lang.IPersistentMap context,
                     clojure.lang.IPersistentCollection content);
}