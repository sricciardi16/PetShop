package it.petshop.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectFilter extends HttpFilter {
	private static final String REDIRECT_PARAMETER = "redirect";
	private static final String REDIRECT_ATTRIBUTE = "redirect";

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String redirectPath = request.getParameter(REDIRECT_PARAMETER);
		if (redirectPath == null)
			redirectPath = (String) request.getAttribute(REDIRECT_ATTRIBUTE);

		if (redirectPath != null)
			response.sendRedirect(redirectPath);
		else
			chain.doFilter(request, response);
	}
}
