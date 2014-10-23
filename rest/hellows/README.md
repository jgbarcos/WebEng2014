# Hello World! Server (standalone)
This project contains a demonstration of a web service build with [JAX-RS](http://docs.oracle.com/javaee/6/tutorial/doc/giepu.html) 
that can run standalone. That is, it does not require to be deployed in a server.

Run the code with ```gradle server``` and then navigate to [http://localhost:8080/helloWorld/sayHello/World](http://localhost:8080/helloWorld/sayHello/World) to 
retrieve a text containing the word `World`. 

The code of the service is:
```java
@Path("sayHello/{name}")
public class HelloWebService {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getMessage(@PathParam("name") String name) {
		return "Hello "+name;
	}
}
```

Therefore, if `World` is changed by `me`, the returned message will be `Hello me
