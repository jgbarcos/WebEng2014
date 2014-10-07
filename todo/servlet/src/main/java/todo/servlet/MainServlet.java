package todo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/query" })
public class MainServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String title = req.getParameter("title");
		String project = req.getParameter("project");
		String content = req.getParameter("content");
		String priority = req.getParameter("priority");
		String status = req.getParameter("status");
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html><head><title>Query not Implemented</title></head>"
				+ "<body>"
				+ "<h1>Detected Fields:</h1>"
				+ "Title: " + title + "<br />"
				+ "Project: " + project + "<br />"
				+ "Content: " + content + "<br />"
				+ "Priority: " + priority + "<br />"
				+ "Status: " + status + "<br />"
				+ "</html>"
				+ "</body>");
	}
}
