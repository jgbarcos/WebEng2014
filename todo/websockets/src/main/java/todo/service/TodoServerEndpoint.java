package todo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import todo.model.GsonIO;
import todo.model.TaskPriority;
import todo.model.TaskStatus;
import todo.model.TodoList;
import todo.model.TodoTask;
import todo.model.TransferTask;

@ServerEndpoint(value = "/todo")
public class TodoServerEndpoint {

	private static Logger logger = Logger.getLogger(TodoServerEndpoint.class.getName());
	private static Gson gson = null;
	
	/*
	 * WebSockets Simple CRUD Protocol of TodoTasks
	 */
	// TASK definition: "id | title | end_date | project |  content |  priority | status"
	// ID definition: " integer "
	// Operations
	private final String CREATE = "CREATE"; // CREATE $ (TASK &)+
	private final String DELETE = "DELETE"; // DELETE $ (ID &)+
	private final String EDIT = "EDIT"; 	// EDIT $ (TASK &)+
	private final String LIST = "LIST"; 	// LIST
	private final String GET = "GET"; 		// GET $ (ID &)+
	private final String ERROR = "ERROR"; 	// ERROR $ error_code | message
	private final String QUIT = "QUIT"; 	// QUIT

	// Separators
	private final String CMD_SEP = "$";
	private final String PROP_SEP = "|";
	private final String TASK_SEP = "&";
	
	// Error codes
	private final String BAD_REQUEST = "BAD_REQUEST";
	private final String NOT_FOUND = "NOT_FOUND";
	
	private final static String DEFAULT_FILE_NAME = "todoList.json";
	private final static TaskPriority DEFAULT_PRIORITY = TaskPriority.NORMAL;
	private final static TaskStatus DEFAULT_STATUS = TaskStatus.PENDING;


	@OnOpen
	public void onOpen(Session session) {
		logger.info("Connected ... " + session.getId());
		sendListTo(session);
	}

	@OnMessage
	public void onMessage(String message, final Session session) {
		// Check message correctness or client quit
		if(message.equals(QUIT)){
			try {
				session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE,
						"Client  \""+session.getId()+"\" disconnects"));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return;
		}
		
		String[] cmdSplit = message.split(Pattern.quote(CMD_SEP));
		String command = cmdSplit[0].trim().toUpperCase();
		
		switch (command) {
		case CREATE:
		case EDIT:
			if(cmdSplit.length > 1){
				// Perform Action
				String response = command+CMD_SEP;
				ArrayList<TodoTask> taskArray = decodeList(cmdSplit[1], PROP_SEP, TASK_SEP);
				for(TodoTask task : taskArray){
					if(threadSafeUpdate(command, task) != -1){
						response+=encodeTask(task, PROP_SEP)+TASK_SEP;
					}
				}
				// Send changes to all
				if(response.length() > (command+CMD_SEP).length()){
					sendToAll(response, session);
				}
				else{
					sendErrorTo(session, BAD_REQUEST, "Provided arguments contains errors");
				}				
			}
			else{
				sendErrorTo(session, BAD_REQUEST, command+" needs separator + \""+ CMD_SEP +"\" and arguments");
			}
			break;
		case DELETE:
			if(cmdSplit.length > 1){
				// Perform Action
				String response = command+CMD_SEP;
				String[] splitID = cmdSplit[1].split(Pattern.quote(TASK_SEP));
				for(String idString : splitID){
					TodoTask task = new TodoTask();
					task.setId(Integer.parseInt(idString));
					if(threadSafeUpdate(command, task) != -1){
						response+=idString+TASK_SEP;
					}
				}
				// Send changes to all
				if(response.length() > (command+CMD_SEP).length()){
					sendToAll(response, session);
				}
				else{
					sendErrorTo(session, NOT_FOUND, "Provided Task IDs does not exists");
				}
			}
			else{
				sendErrorTo(session, BAD_REQUEST, command+" needs separator + \""+ CMD_SEP +"\" and arguments");
			}
			break;
		case LIST:
			sendListTo(session);
			break;
		case GET:
			TodoList todoList = readTodoList();
			if(cmdSplit.length > 1){
				// Perform Action
				String response = command+CMD_SEP;
				String[] splitID = cmdSplit[1].split(Pattern.quote(TASK_SEP));
				for(String idString : splitID){
					int id = Integer.parseInt(idString);
					TodoTask task = todoList.getTask(id);
					if(task != null){
						response+=encodeTask(task, PROP_SEP)+TASK_SEP;
					}
				}
				// Send changes to all
				if(response.length() > (command+CMD_SEP).length()){
					session.getAsyncRemote().sendText(response);
				}
				else{
					sendErrorTo(session, NOT_FOUND, "Provided Task IDs does not exists");
				}
			}
			else{
				sendErrorTo(session, BAD_REQUEST, command+" needs separator + \""+ CMD_SEP +"\" and arguments");
			}
			break;
		case ERROR:
			if(cmdSplit.length > 1){
				logger.log(Level.FINEST, "Error message from Client \"" + session.getId() +"\":"+ cmdSplit[1]);
			}
			else{
				logger.log(Level.FINEST, "Empty Error message from Client \"" + session.getId() +"\"");
			}
			break;
		default:
			logger.log(Level.WARNING, "Received wrong command: \""+command+"\"");
			sendErrorTo(session, BAD_REQUEST, "Command is not valid");
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
	
	/*
	 * Non-WebSockets Methods:
	 */
	public static void sendToAll(String message, Session session){
		for (Session s : session.getOpenSessions()) {
			try{
				if(s.isOpen()){
					s.getBasicRemote().sendObject(message);
				}
			} catch (IOException | EncodeException e){
				logger.log(Level.WARNING, "message to \""+s.getId()+"\" client failed");
			}
		}
	}
	
	private void sendListTo(final Session session) {
		TodoList todoList = readTodoList();
		if(todoList != null){
			String response = LIST + CMD_SEP;
			for(TodoTask task : todoList.getTaskList()){
				response += encodeTask(task,PROP_SEP) + TASK_SEP;
			}
			session.getAsyncRemote().sendText(response);
		}
		else{
			logger.log(Level.SEVERE, "Data error: TodoList is null");
			sendErrorTo(session, NOT_FOUND, "Todo List is Empty");
		}
		return;
	}
	
	private void sendErrorTo(final Session session, String code, String msg){
		session.getAsyncRemote().sendText(ERROR+CMD_SEP+code+PROP_SEP+msg);
	}
	
	public static ArrayList<TodoTask> decodeList(String message, String propDelimiter, String taskDelimiter){
		String[] msgArray = message.split(Pattern.quote(taskDelimiter));
		ArrayList<TodoTask> taskArray = new ArrayList<TodoTask>();
		for(int i=0; i<msgArray.length; i++){
			TodoTask task = decodeTask(msgArray[i], propDelimiter);
			if(task != null){
				taskArray.add(task);
			}
		}
		return taskArray;
	}
	
	public static TodoTask decodeTask(String message, String delimiter){
		String[] msgArray = message.split(Pattern.quote(delimiter));
		if(msgArray.length != 7){
			logger.log(Level.SEVERE, "Cannot decode message: \""+ message +"\" with delimiter: \"" + delimiter + "\"");
			return null;
		}
		
		TransferTask transfer = new TransferTask();
		int i=0;
		transfer.setId(Integer.parseInt(msgArray[i]));i++;
		transfer.setTitle(msgArray[i]);i++;
		transfer.setEndDate(msgArray[i]);i++;
		transfer.setProject(msgArray[i]);i++;
		transfer.setContent(msgArray[i]);i++;
		transfer.setPriority(msgArray[i]);i++;
		transfer.setStatus(msgArray[i]);
		
		return transfer.retrieveTask(DEFAULT_PRIORITY, DEFAULT_STATUS);
	}
	
	// CreateTask $ id | title | end_date | project |  content |  priority | status
	public static String encodeTask(TodoTask task, String delimiter){
		return task.getId() + delimiter + 
				nullSafe(task.getTitle()) + delimiter + 
				nullSafe(task.getEndDate()) + delimiter + 
				nullSafe(task.getProject()) + delimiter + 
				nullSafe(task.getContent()) + delimiter + 
				nullSafe(task.getPriority()) + delimiter + 
				nullSafe(task.getStatus());
	}
	
	private static String nullSafe(Object obj) {
		if (obj == null)
			return "";
		else
			return obj.toString();
	}
	
	private TodoList readTodoList() {
		if (gson == null) {
			System.out.println("Creating GSON object");
			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		}
		return GsonIO.loadFile(gson, DEFAULT_FILE_NAME);
	}

	private void saveTodoList(TodoList todoList) {
		if (gson == null) {
			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		}
		GsonIO.saveFile(gson, DEFAULT_FILE_NAME, todoList);
	}

	private synchronized int threadSafeUpdate(String job, TodoTask task) {
		// Create a new Task or edit an existing one
		TodoList todoList = null;
		switch (job){
			case CREATE :
				todoList = readTodoList();
				if(task.getId() < 0){
					todoList.setID(task);
				}
				else if(todoList.containsTask(task.getId())){
					return -1;
				}
				
				todoList.addTask(task);
				System.out.println("Task " + task.getId() + " Created");
				saveTodoList(todoList);
				return task.getId();
				
			case EDIT :
				todoList = readTodoList();
				if (task.getId() >= 0 && todoList.removeTask(task.getId())) {
					todoList.addTask(task);
					System.out.println("Task " + task.getId() + " Updated");
					saveTodoList(todoList);
					return task.getId();
				}
				break;
				
			case DELETE :
				todoList = readTodoList();
				if (task.getId() >= 0 && todoList.removeTask(task.getId())) {
					System.out.println("Task " + task.getId() + " Removed");
					saveTodoList(todoList);
					return task.getId();
				}
				break;
			default :
				return -1;
		}

		// Return -1 if action didnt provoke changes
		return -1;
	}
}
