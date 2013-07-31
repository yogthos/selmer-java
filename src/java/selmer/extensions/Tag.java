package selmer.extensions;

import java.util.List;
import java.util.Map;

public interface Tag<T,K,V> {
  public String render(List<T> args, Map<K,V> context);
}