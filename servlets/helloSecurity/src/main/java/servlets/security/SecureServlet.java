package servlets.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/" })
public class SecureServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.authenticate(resp)) {
			StringBuffer sb = new StringBuffer();
			sb.append("<html><head><title>Secure Hello ");
			sb.append(req.getRemoteUser());
			sb.append(" autenticated with ");
			sb.append(req.getAuthType());
			sb.append("!</title></head><body><h1>");
			sb.append(req.getRemoteUser());
			sb.append(" autenticated with ");
			sb.append(req.getAuthType());
			sb.append(", you have  ");
			sb.append(req.isUserInRole("admin") ? "the role of administrator"
					: "an unknown role");
			sb.append("</h1></body></html>");

			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			out.write(sb.toString());

		} else {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
}
