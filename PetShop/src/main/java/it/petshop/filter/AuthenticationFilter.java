package it.petshop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AuthenticationFilter extends HttpFilter {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("nomeUtente") == null) {
			request.setAttribute("status", "error");
			request.setAttribute("message", "Non hai effettuato il login");
			request.getRequestDispatcher("/").forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}
}
