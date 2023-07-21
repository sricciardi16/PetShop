package it.petshop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.petshop.utility.RedirectUtil;

public class LoggedRedirectFilter extends HttpFilter {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = request.getSession();

		if (session.getAttribute("nomeUtente") != null) {

			if (session.getAttribute("amministratore") != null) {
				response.sendRedirect(request.getContextPath() + "/admin");
			} else {
				if (!RedirectUtil.redirectToStoredUrl(session, response))
					response.sendRedirect(request.getContextPath() + "/");
			}

		} else {
			chain.doFilter(request, response);
		}

	}

}
