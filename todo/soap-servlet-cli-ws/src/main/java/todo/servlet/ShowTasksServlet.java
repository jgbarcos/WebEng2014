package todo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceException;

import todo.model.TodoTask;
import todo.service.TodoWebService;
import todo.service.TransferTask;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/query" })
public class ShowTasksServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Get parameters
		String title = req.getParameter("title");
		String project = req.getParameter("project");
		String content = req.getParameter("content");
		String priority = req.getParameter("priority");
		String status = req.getParameter("status");

		// Connect to soap
		TodoWebService tws = null;
		try{
			tws = todo.service.Client.createTodoWebService();
		}catch(WebServiceException e){
			resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return;
		}
		
		// Retrieve filter results as TransferTasks
		List<TransferTask> transferList = tws.filterTasks(title, project, content, priority, status);
		
		// Convert TransferTasks to TodoTasks
		List<TodoTask> results = new ArrayList<TodoTask>();
		for(TransferTask transfer : transferList){
			results.add(new TodoTask(transfer));
		}
		
		// Set parameters
		resp.setContentType("text/html");
		req.setAttribute("results", results);
		
		// Request jsp dispatcher
		RequestDispatcher dispatcher1 = req.getRequestDispatcher("ShowTaskList.jsp");
		dispatcher1.forward(req,resp);
	}
}
