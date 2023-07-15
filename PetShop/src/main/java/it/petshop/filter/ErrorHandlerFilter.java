package it.petshop.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.petshop.utility.DataHelper;
import it.petshop.utility.PetShopException;
import it.petshop.utility.Util;

public class ErrorHandlerFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception exception) {
        	int errorCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            if (exception instanceof PetShopException)
                errorCode = ((PetShopException)exception).getErrorCode();
            if (Util.isAjaxRequest(request)) {
            	response.setStatus(errorCode);
            	DataHelper errorData = new DataHelper();
            	errorData.add("message", exception.getMessage());
            	errorData.sendAsJSON(response);
            } else {
            	response.sendError(errorCode, exception.getMessage());
            }
            return;
        }
    }
}
