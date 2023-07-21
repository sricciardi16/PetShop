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

		HttpSession session = request.getSession();

		if (session.getAttribute("amministratore") == null) {
			// messaggio per dire che non è un amminsitratore
						response.sendRedirect(request.getContextPath() + "/");
			
		} else {
			chain.doFilter(request, response);
		}

	}

}
