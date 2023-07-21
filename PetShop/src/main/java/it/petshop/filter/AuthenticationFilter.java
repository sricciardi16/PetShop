package it.petshop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.petshop.utility.RedirectUtil;

public class AuthenticationFilter extends HttpFilter {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = request.getSession();
		if (session.getAttribute("nomeUtente") == null) {

			RedirectUtil.storeCurrentUrlForRedirect(request);

			response.sendRedirect(request.getContextPath() + "/login");

		} else {
			chain.doFilter(request, response);
		}
	}
}
