package events.hellows;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

import javax.websocket.DeploymentException;

import org.glassfish.tyrus.client.ClientManager;

public class Client {

	static CountDownLatch latch;

	public static void main(String[] args) {
		latch = new CountDownLatch(1);
	
		ClientManager client = ClientManager.createClient();
		try {
			client.connectToServer(WordgameClientEndpoint.class, new URI(
					"ws://localhost:8025/websockets/game"));
			latch.await();
	
		} catch (DeploymentException | URISyntaxException
				| InterruptedException | IOException e) {
			throw new RuntimeException(e);
		}
	}

}
