package it.petshop.utility;

import javax.servlet.http.HttpServletRequest;

public class AjaxUtil {

	public static boolean isAjaxRequest(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}
}
