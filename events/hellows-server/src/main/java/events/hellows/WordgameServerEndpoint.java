package events.hellows;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/game")
public class WordgameServerEndpoint {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	@OnOpen
	public void onOpen(Session session) {
		logger.info("Connected ... " + session.getId());
	}

	/*@OnMessage
	public String onMessage(String message, Session session) {
		switch (message) {
		case "quit":
			try {
				session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE,
						"Game ended"));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			break;
		}
		return message;
	}*/
	
	@OnMessage
	public void onMessage(String message, final Session session) {
		switch (message) {
		case "quit":
			try {
				session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE,
						"Game ended"));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			break;
		default:
			for (Session s : session.getOpenSessions()) {
				try{
					if(s.isOpen()){
						s.getBasicRemote().sendObject(message);
					}
				} catch (IOException | EncodeException e){
					logger.log(Level.WARNING, "onMessage to a  client failed");
				}
			}
		}
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s closed because of %s",
				session.getId(), closeReason));
	}
	
	@OnError
    public void onError(Session session, Throwable t) {
        logger.log(Level.SEVERE, "Error: Conexion cerrada");
    }
}
