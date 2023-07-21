package it.petshop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.petshop.utility.AjaxUtil;
import it.petshop.utility.JsonResponseHelper;
import it.petshop.utility.PetShopException;

public class ErrorHandlerFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (Exception exception) {
			int errorCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
			if (exception instanceof PetShopException)
				errorCode = ((PetShopException) exception).getErrorCode();
			if (AjaxUtil.isAjaxRequest(request)) {
				response.setStatus(errorCode);
				JsonResponseHelper errorData = new JsonResponseHelper();
				errorData.add("message", exception.getMessage());
				errorData.send(response);
			} else {
				response.sendError(errorCode, exception.getMessage());
			}
			return;
		}
	}
}
