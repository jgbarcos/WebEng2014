package todo.service;

import javax.xml.ws.Endpoint;

public class Server {
	
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8081/todo", new TodoWebService());
	}

}
