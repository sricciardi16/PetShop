package it.petshop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoAdminFilter extends HttpFilter {

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request.getSession(false) != null && request.getSession(false).getAttribute("amministratore") != null) {
			response.sendRedirect(request.getContextPath() + "/admin");
			return;
		}
		chain.doFilter(request, response);
	}
}
