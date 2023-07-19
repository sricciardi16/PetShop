package it.petshop.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NotAdminFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("amministratore") == null) {
			chain.doFilter(request, response);
		} else {
			String status = "error";
			String message = "Sei un Amministratore!";

			String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
			response.sendRedirect(request.getContextPath() + "/login?status=" + status + "&message=" + encodedMessage);
		}

	}
}
