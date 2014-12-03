package todo.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceException;

import todo.service.TodoWebService;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = {"/remove"})
public class RemoveTaskServlet  extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Get parameters
		String taskID = req.getParameter("taskID");
		
		// String explaining the operation result
		String operationResult = "";
		
		int id = -1;
		if(taskID != null){
			try{
				id = Integer.parseInt(taskID.trim());
			}
			catch(NumberFormatException e){}
		}
		
		// Valid argument try to perform remove operation
		if(id >= 0){
			// Connect to soap
			TodoWebService tws = null;
			try{
				tws = todo.service.Client.createTodoWebService();
			}catch(WebServiceException e){
				resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
				return;
			}
			
			// Perform operation and check if operation is successful
			if( tws.removeTask(id) >= 0){
				operationResult = "Task successfully removed";
			}
			// Task doesnt not exists, cant be deleted
			else{
				operationResult = "Task does not exist, cant perform the remove operation";
			}
		}
		// Invalid argument
		else{
			operationResult = "Invalid task ID argument";
		}
		
		// Set parameters
		resp.setContentType("text/html");
		req.setAttribute("operationResult", operationResult);
		
		// Request jsp dispatcher
		RequestDispatcher dispatcher1 = req.getRequestDispatcher("OperationResult.jsp");
		dispatcher1.forward(req,resp);
	}
}
