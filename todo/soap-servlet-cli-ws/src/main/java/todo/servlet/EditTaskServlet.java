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
import todo.service.TransferTask;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/edit" })
public class EditTaskServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Get parameters
		String taskID = req.getParameter("taskID");
		String title = req.getParameter("title");
		String project = req.getParameter("project");
		String endDate = req.getParameter("endDate");
		String content = req.getParameter("content");
		String priority = req.getParameter("priority");
		String status = req.getParameter("status");

		// String explaining the operation result
		String operationResult = "";

		int id = -1;
		if (taskID != null) {
			try {
				id = Integer.parseInt(taskID.trim());
			} catch (NumberFormatException e) {
				
			}
		}

		// Valid argument try to perform remove operation
		if (id >= 0) {
			// Create Transfer Task
			TransferTask transfer = new TransferTask();
			transfer.setId(id);
			transfer.setTitle(title);
			transfer.setProject(project);
			transfer.setEndDate(endDate);
			transfer.setContent(content);
			transfer.setPriority(priority);
			transfer.setStatus(status);
			
			// Connect to soap
			TodoWebService tws = null;
			try {
				tws = todo.service.Client.createTodoWebService();
			} catch (WebServiceException e) {
				resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
				return;
			}

			// Perform operation and check if operation is successful
			if (tws.editTask(transfer) >= 0) {
				operationResult = "Task successfully edited";
			}
			// Task does not exists, then cant be edited
			else {
				operationResult = "Task does not exist, cant perform the edit operation";
			}
		}
		// Invalid argument
		else {
			operationResult = "Invalid task ID argument";
		}

		// Set parameters
		resp.setContentType("text/html");
		req.setAttribute("operationResult", operationResult);

		// Request jsp dispatcher
		RequestDispatcher dispatcher1 = req.getRequestDispatcher("OperationResult.jsp");
		dispatcher1.forward(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String operationResult = null;
		TodoWebService tws = null;
		
		// Get task
		String taskID = req.getParameter("taskID");
		
		try{
			tws = todo.service.Client.createTodoWebService();
		}catch(WebServiceException e){
			resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return;
		}
			
		int id = -1;
		if (taskID != null) {
			try {
				id = Integer.parseInt(taskID.trim());
			} catch (NumberFormatException e) {
				
			}
		}
		
		// Check if task exists
		TransferTask transfer = null;
		if(id < 0){
			operationResult = "Invalid task ID argument";
		}
		else{
			transfer = tws.getTask(id);	
			
			if(transfer == null){
				operationResult = "Task does not exist, cant retrieve the task";
			}
		}
			
		// Everything is ok
		if (operationResult == null){
			// Set parameters
			resp.setContentType("text/html");
		
			// Set attributes
			req.setAttribute("taskID", taskID);
			req.setAttribute("title", transfer.getTitle());
			req.setAttribute("project", transfer.getProject());
			req.setAttribute("endDate",  transfer.getEndDate());
			req.setAttribute("content", transfer.getContent());
			req.setAttribute("priority", transfer.getPriority());
			req.setAttribute("status", transfer.getStatus());	
			
			req.setAttribute("htmlTitle", "Edit Task");
			req.setAttribute("htmlButtonAction", "edit");
	
			// Request jsp dispatcher
			RequestDispatcher dispatcher1 = req.getRequestDispatcher("TaskForm.jsp");
			dispatcher1.forward(req, resp);
		}
		// Any error => operationResult != null
		else{
			// Set parameters
			resp.setContentType("text/html");
			req.setAttribute("operationResult", "");
			
			// Request jsp dispatcher
			RequestDispatcher dispatcher1 = req.getRequestDispatcher("OperationResult.jsp");
			dispatcher1.forward(req,resp);
		}
	}
}
