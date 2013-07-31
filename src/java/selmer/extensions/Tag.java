package selmer.extensions;

public interface Tag {
  public String render(java.util.List args,
                       java.util.Map context);
}