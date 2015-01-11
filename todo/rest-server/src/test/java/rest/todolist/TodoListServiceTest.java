package rest.todolist;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.junit.After;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import rest.service.ApplicationConfig;
import rest.model.GsonIO;
import rest.model.TodoList;

/**
 * A simple test suite
 *
 */
public class TodoListServiceTest {

	HttpServer server;

	@Test
	public void serviceIsAlive() throws IOException {
		// Prepare server
		TodoList todoList = new TodoList();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		GsonIO gsonIO = new GsonIO(gson, "test.json");
		launchServer(todoList, gsonIO);

		// Request the address book
		Client client = ClientBuilder.newClient();
		Response response = client.target("http://localhost:8282")
				.request().get();
		assertEquals(200, response.getStatus());
		assertEquals(0, response.readEntity(TodoList.class).getTaskList().size());
	}

	private void launchServer(TodoList todoList, GsonIO gsonIO) throws IOException {
		URI uri = UriBuilder.fromUri("http://localhost/").port(8282).build();
		server = GrizzlyHttpServerFactory.createHttpServer(uri,
				new ApplicationConfig(todoList, gsonIO));
		server.start();
	}

	@After
	public void shutdown() {
		if (server != null) {
			server.shutdownNow();
		}
		server = null;
	}

}
