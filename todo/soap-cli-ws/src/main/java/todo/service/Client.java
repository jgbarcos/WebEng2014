package todo.service;

// import javax.xml.ws.BindingProvider;

public class Client {
 	
	public static void main(String[] args) {
		TodoWebServiceService twss = new TodoWebServiceService();
		TodoWebService tws = twss.getTodoWebServicePort();

//		// If there is a TCP/IP Monitor 8090 -> 8080
//		String endpointURL = "http://localhost:8080/hellows/HelloWorld";
//		BindingProvider bp = (BindingProvider)hws;
//		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
		
		System.out.println(tws.listTask());
	}
}
