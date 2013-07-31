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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yogthos.Selmer;

import selmer.extensions.Tag;
import selmer.extensions.Filter;

public class Main {

	static class MyTag implements Tag {

		public String render(List args, Map context) {			
			return args.get(0) + "=>" + context.get("foo");
		}		
	}
	
	static class MyFilter implements Filter {
				
		public String render(List args) {			
			return args.get(0).toString().toUpperCase();
		}
		
	}
	
	
	public static void main(String[] args) {

		Map<String, Object> m = new HashMap<String,Object>();
		m.put("foo", Arrays.asList("bar"));
		
		Selmer.addTag("x", new MyTag());
		Selmer.addFilter("embiginate", new MyFilter());
		
		System.out.println(Selmer.render("{% x y z %} {{foo|embiginate}}", m));
	}

}
```

The API provides `render` method for rendering string content and the `render-file` method for rendering file templates
as well as `addTag` and `addFilter` methods.
