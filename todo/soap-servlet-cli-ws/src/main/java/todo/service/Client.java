package todo.service;

// import javax.xml.ws.BindingProvider;

public class Client {
 	public static TodoWebService createTodoWebService(){
		TodoWebServiceService twss = new TodoWebServiceService();
		return twss.getTodoWebServicePort();
 	}
}
