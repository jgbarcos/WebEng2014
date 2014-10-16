package servlets.hello;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/hi" })
public class HelloThreadSafe extends HttpServlet {

	private AtomicInteger thisIsNotThreadSafe = new AtomicInteger();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String thisIsThreadSafe = req.getParameter("name");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html><head><title>Hello " + thisIsThreadSafe
				+ "!</title></head>" + "<body><h1>Hello " + thisIsThreadSafe
				+ " (" + thisIsNotThreadSafe.incrementAndGet()
				+ " times)!</h1></body></html>");
	}

}
