package todo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.GsonBuilder;

import todo.model.GsonIO;
import todo.model.HelperMethods;
import todo.model.TaskPriority;
import todo.model.TaskStatus;
import todo.model.TodoTask;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/query" })
public class MainServlet extends HttpServlet {
	
	private final static String DEFAULT_FILE_NAME = "todoList.json";
	
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
		TaskPriority priority = TaskPriority.findPriority(req.getParameter("priority"));
		TaskStatus status = TaskStatus.findStatus(req.getParameter("status"));
		
		// Get GsonIO, provides loadFile and saveFile functionality
		GsonIO gsonIO = new GsonIO(new GsonBuilder().create(), DEFAULT_FILE_NAME);
		
		// Apply Filters to Search
		List<TodoTask> results = HelperMethods.filterSearch(gsonIO.loadFile(), title, project, content, priority, status);
		
		
		resp.setContentType("text/html");
		
		/*// Request jsp dispatcher
		req.setAttribute("results", results);
		RequestDispatcher dispatcher1 = req.getRequestDispatcher("");
		dispatcher1.forward(req,resp);*/
		
		//TODO: Better use JSP for this:		
		PrintWriter out = resp.getWriter();
		String output = "<html><head><title>Query Results</title></head>"
				+ "<body>"
				+ "<h1>Query Results:</h1>";
		
		for(TodoTask task : results){
			output += printTask(task);
		}
		
		if(results.size() == 0){
			output += "No results from provided filters";
		}
		
		output += "</html>"
				+ "</body>";
		
		out.println(output);

	}
	
	private String printTask(TodoTask task){
		return task.toString("","<br />")
				+ "----------------------------------------" + "<br />";
		
		/*"Title: " + task.getTitle() + "<br />"
				+ "End Date: " + task.getEndDate() + "<br />"
				+ "Project: " + task.getProject() + "<br />"
				+ "Content: " + task.getContent() + "<br />"
				+ "Priority: " + task.getPriority().toString() + "<br />"
				+ "Status: " + task.getStatus().toString() + "<br />"
				+ "----------------------------------------" + "<br />";*/
	}
}
