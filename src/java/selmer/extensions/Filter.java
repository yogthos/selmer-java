package selmer.extensions;

public interface Filter<T> {
  public String render(java.util.List<T> args);

}