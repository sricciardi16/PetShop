package it.petshop.utility;

import javax.servlet.http.HttpServletRequest;

public class Util {

	public static boolean isAjaxRequest(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}
}
