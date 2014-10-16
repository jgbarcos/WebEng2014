package bigws.hellows;

// import javax.xml.ws.BindingProvider;

public class Client {
 	
	public static void main(String[] args) {
		HelloWebServiceService hwss = new HelloWebServiceService();
		HelloWebService hws = hwss.getHelloWebServicePort();

//		// If there is a TCP/IP Monitor 8090 -> 8080
//		String endpointURL = "http://localhost:8080/hellows/HelloWorld";
//		BindingProvider bp = (BindingProvider)hws;
//		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);

		
		System.out.println(hws.sayHello("World!"));
	}

}
