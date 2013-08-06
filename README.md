selmer-java
===========

Java wrapper for [Selmer](https://github.com/yogthos/Selmer)

## Usage

Install with Maven

```xml
<repository>
  <id>clojars.org</id>
  <url>http://clojars.org/repo</url>
</repository>

<groupId>selmer-java</groupId>
	<artifactId>selmer-java</artifactId>
	<version>0.6</version>
</dependency>
```

Then you can start using it as seen in the example below:

```java
import java.util.Arrays;
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

    static class PersonFilter implements Filter<Map<String,String>> {

        public String render(List<Map<String,String>> args) {           
            String firstName = args.get(0).get("firstName");
            String lastName = args.get(0).get("lastName");
            return lastName.toUpperCase() + ", " + firstName;
        }
    }

    public static class Person {
        private String firstName;
        private String lastName;
        private Address address;

        public Person(String firstName, String lastName, Address address) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.setAddress(address);
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

		public Address getAddress() {
			return address;
		}

		public void setAddress(Address address) {
			this.address = address;
		}
        
       
    }
    
    public static class Address {
    	private String street;
    	public Address(String street) {
    		this.setStreet(street);
    	}
		public String getStreet() {
			return street;
		}
		public void setStreet(String street) {
			this.street = street;
		}
    	
    }
    public static void main(String[] args) {

        Map<String, Object> m = new HashMap<String,Object>();
        m.put("persons", Arrays.asList(new Person("John", "Doe", new Address("Welsely")),
        		                       new Person("Jane", "Doe", new Address("Wellington"))));
        m.put("name", "Bob");
        m.put("v", "some value");
        m.put("smalltext", "some text");

        Selmer.addTag("x", new MyTag());
        Selmer.addBlockTag("foo", "endfoo", new MyBlockTag());
        Selmer.addFilter("formatPerson", new PersonFilter());

        String template =
        //variables
        "{{name}}\n" +
        //conditions
        "{% if v %}we have {{v}}{%else%}no v{% endif %}\n" +
        //loops
        "{% for person in persons %} {{person.firstName}} lives on {{person.address.street}}\n{% endfor %}\n" +
        //custom filters
        "{% for person in persons %} {{person|formatPerson}} {% endfor %}\n" +
        //custom tags
        "{% x y z %}{% foo %}foo body {{smalltext|upper}}{% endfoo %}";

        System.out.println(Selmer.render(template, m));
    }
}
```
The above example will produce the following output:

```
Bob
we have some value
 John lives on Welsely
 Jane lives on Wellington

 DOE, John  DOE, Jane 
y->zfoo body SOME TEXT
```

The API provides `render` method for rendering string content and the `render-file` method for rendering file templates
as well as `addTag`, `addBlockTag`, and `addFilter` methods.

The context map can consist of lists, maps, or beans, or Java primitives.
