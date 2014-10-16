# Hello World! Server (web application)
This project contains a demonstration of a web service build with [JAX-WS](http://docs.oracle.com/javaee/6/tutorial/doc/bnayl.html). 

Deploy your code to a [Jetty](http://www.eclipse.org/jetty/) server with ```gradle jettyEclipseRun```. Then, open the page at [http://localhost:8080/](http://localhost:8080/). 

The code is quite simple:
```java
@WebService
public class HelloWebService {
	
	@WebMethod()
	public String sayHello(String name) {
		return "Hello "+name +"!";
	}
}
```

The service endpoint is at [http://localhost:8080/helloWorld](http://localhost:8080/helloWorld) (see [WEB-INF/sun-jaxws.xml](src/main/webapp/WEB-INF/sun-jaxws.xml) for the endpoint mapping). 

Note that [WEB-INF/web.xml](src/main/webapp/WEB-INF/web.xml) has a ```<servlet-mapping>``` entry that matches ```/hello*``` to requests to the JAX-WS runtime.
