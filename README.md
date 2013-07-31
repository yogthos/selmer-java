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
import java.util.List;
import java.util.Map;

import org.yogthos.Selmer;

import selmer.extensions.BlockTag;
import selmer.extensions.Tag;
import selmer.extensions.Filter;


public class Main {

	static class MyTag implements Tag<String,String,Map<String,String>> {

        public String render(List<String> args, Map<String,Map<String,String>> context) {        	
            return args.get(0) + "->" + args.get(1);
        }       
    }

	static class MyBlockTag implements BlockTag<String,String,Map<String,String>> {

		@Override
		public String render(List<String> args,
				Map<String, Map<String, String>> context,
				Map<String, Map<String, String>> content) {
			return content.get("foo").get("content");
		}		
	}
	
    static class MyFilter implements Filter<Map<String,String>> {
    	
        public String render(List<Map<String,String>> args) {        	
        	String firstName = args.get(0).get("firstName");
        	String lastName = args.get(0).get("lastName");
        	return lastName.toUpperCase() + ", " + firstName;
        }
    }

    public static class Person {
    	private String firstName;
    	private String lastName;
    	
    	public Person(String firstName, String lastName) {
    		this.firstName = firstName;
    		this.lastName = lastName;
    	}
    	
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
    }
    public static void main(String[] args) {

        Map<String, Object> m = new HashMap<String,Object>();
        m.put("foo", new Person("Foo", "Bar"));
        
        Selmer.addTag("x", new MyTag());
        Selmer.addBlockTag("foo", "endfoo", new MyBlockTag());
        Selmer.addFilter("embiginate", new MyFilter());

        System.out.println("Result: " + Selmer.render("{% x y z %} {% foo %} {{foo|embiginate}} {% endfoo %}", m));
    }
}

```

The API provides `render` method for rendering string content and the `render-file` method for rendering file templates
as well as `addTag` and `addFilter` methods.
