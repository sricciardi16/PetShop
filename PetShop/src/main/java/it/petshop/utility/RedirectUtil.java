package it.petshop.utility;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RedirectUtil {
	
	public static final String URL_TO_REDIRECT_PARAM_NAME = "requester";
	
	private RedirectUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static void storeCurrentUrlForRedirect(HttpServletRequest request) {
		String requester = request.getRequestURI();
		if (request.getQueryString() != null)
			requester += "?" + request.getQueryString();

		request.getSession().setAttribute(URL_TO_REDIRECT_PARAM_NAME, requester);
	}
	
	public static void storeUrlForRedirect(HttpServletRequest request, String url) {
		request.getSession().setAttribute(URL_TO_REDIRECT_PARAM_NAME, url);
	}
	
	
	public static boolean redirectToStoredUrl(HttpSession session, HttpServletResponse response) throws IOException {
		String requester = (String) session.getAttribute(URL_TO_REDIRECT_PARAM_NAME);
		session.removeAttribute(URL_TO_REDIRECT_PARAM_NAME);

		if (requester != null) {
			response.sendRedirect(requester);
			return true;
		}
		return false;
		
	}

}
