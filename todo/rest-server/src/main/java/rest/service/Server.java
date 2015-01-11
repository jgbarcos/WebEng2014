package rest.service;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import rest.model.GsonIO;
import rest.model.TodoList;

public class Server {
	private static final Logger LOGGER = Grizzly.logger(Server.class);

	private final static String DEFAULT_FILE_NAME = "todoList.json";
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		LOGGER.setLevel(Level.FINER);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		GsonIO gsonIO = new GsonIO(gson, DEFAULT_FILE_NAME);
		TodoList todoList = gsonIO.loadFile();
		
		URI uri = UriBuilder.fromUri("http://localhost/").port(8081).build();
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri,
				new ApplicationConfig(todoList, gsonIO));
		try {
			server.start();
			LOGGER.info("Press 's' to shutdown now the server...");
			while(true){
				int c = System.in.read();
				if (c == 's')
					break;
			}
		} catch (IOException ioe) {
			LOGGER.log(Level.SEVERE, ioe.toString(), ioe);
		} finally {
			// TODO DEBUG : Save file just in case is not correctly saved
			gsonIO.saveFile(todoList);
			server.stop();
			LOGGER.info("Server stopped");
		}
	}
}
