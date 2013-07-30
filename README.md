selmer-java
===========

Java wrapper for Selmer

## Usage

Install with Maven

```xml
<repository>
  <id>clojars.org</id>
  <url>http://clojars.org/repo</url>
</repository>

<groupId>selmer-java</groupId>
		<artifactId>selmer-java</artifactId>
		<version>0.2</version>
</dependency>
```

Then can call it using `org.yogthos.Selmer:

```java
import java.util.HashMap;
import java.util.Map;

import org.yogthos.Selmer;

public class Main {

  public static void main(String[] args) {

		Map<String,String> foo = new HashMap<String,String>();
		foo.put("foo", "bar");
		System.out.println(Selmer.render("{{foo}}", foo));
	}
}
```

The API provides `render` method for rendering string content and the `render-file` method for rendering file templates.
