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
@WebServlet(urlPatterns = { "/create" })
public class CreateTaskServlet extends HttpServlet {

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
		String operationResult = null;

		int id = -1;
		if (taskID != null) {
			try {
				id = Integer.parseInt(taskID.trim());
			} catch (NumberFormatException e) {
				operationResult = "Invalid task ID argument";
			}
		}

		
		if(operationResult == null){
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
			if (tws.createTask(transfer) >= 0) {
				operationResult = "Task successfully created";
			}
			// Task does not exists, then cant be deleted
			else {
				operationResult = "Task already exists, cant perform the create operation";
			}
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
		// Set parameters
		resp.setContentType("text/html");
	
		// Set attributes
		req.setAttribute("taskID", "-1");
		req.setAttribute("htmlTitle", "Create Task");
		req.setAttribute("htmlButtonAction", "create");

		// Request jsp dispatcher
		RequestDispatcher dispatcher1 = req.getRequestDispatcher("TaskForm.jsp");
		dispatcher1.forward(req, resp);
	}
}