package rest.hellows;

import javax.ws.rs.client.ClientBuilder;

public class Client {

	public static void main(String[] args) {
		System.out.println(ClientBuilder.newClient()
				.target("http://localhost:8080/helloWorld/sayHello/{name}")
				.resolveTemplate("name", "World!")
				.request("text/plain").get().readEntity(String.class));
	}

}
