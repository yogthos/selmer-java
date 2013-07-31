package selmer.extensions;

import java.util.List;

public interface Filter<T> {
  public String render(List<T> args);

}