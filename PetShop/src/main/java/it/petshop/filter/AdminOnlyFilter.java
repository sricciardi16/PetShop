package it.petshop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.petshop.utility.JsonResponseHelper;

public class AdminOnlyFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("amministratore") != null) {
			chain.doFilter(request, response);
		} else {
			request.setAttribute("status", "error");
			if (session == null || session.getAttribute("utente") != null) {
				request.setAttribute("message", "Effettua prima l'accesso!");
			} else {
				request.setAttribute("message", "Non Sei un Amministratore");
			}

			request.getRequestDispatcher("").forward(request, response);
		}

	}

}
