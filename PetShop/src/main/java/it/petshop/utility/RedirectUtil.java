package it.petshop.utility;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RedirectUtil {
	
	private RedirectUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static void storeCurrentUrlForRedirect(HttpServletRequest request) {
		String requester = request.getRequestURI();
		if (request.getQueryString() != null)
			requester += "?" + request.getQueryString();

		request.getSession().setAttribute("requester", requester);
	}
	
	public static void storeUrlForRedirect(HttpServletRequest request, String url) {
		request.getSession().setAttribute("requester", url);
	}
	
	
	public static boolean redirectToStoredUrl(HttpSession session, HttpServletResponse response) throws IOException {
		String requester = (String) session.getAttribute("requester");
		session.removeAttribute("requester");

		if (requester != null) {
			response.sendRedirect(requester);
			return true;
		}
		return false;
		
	}

}
