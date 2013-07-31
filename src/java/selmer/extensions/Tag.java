package selmer.extensions;

public interface Tag<T,K,V> {
  public String render(java.util.List<T> args, java.util.Map<K,V> context);
}