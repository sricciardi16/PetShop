package it.petshop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.petshop.utility.DataHelper;

public class AuthenticationFilter extends HttpFilter {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("nomeUtente") == null) {
			DataHelper data = new DataHelper();
			data.add("status", "error");
			data.add("message", "Non hai effettuato il login");
			data.setAsRequestAttribute(request);
			request.getRequestDispatcher("/").forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}
}
