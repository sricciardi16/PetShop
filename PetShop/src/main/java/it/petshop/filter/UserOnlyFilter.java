package it.petshop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.petshop.utility.DataHelper;


public class UserOnlyFilter extends HttpFilter {
	
	@Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
        throws IOException, ServletException {
        

        HttpSession session = request.getSession(false);
        
        if (session != null && session.getAttribute("utente") != null) {
            chain.doFilter(request, response);
        } else {
        	
        	DataHelper status = new DataHelper(); 
        	status.add("status", "error");
        	if (session == null || session.getAttribute("utente") == null) {
        		status.add("message", "Effettua prima l'accesso!");
        	} else {
        		status.add("message", "Sei un Amministratore");
        	}
          
        	status.setAsRequestAttribute(request);
           request.getRequestDispatcher("/login").forward(request, response);
        }
        
        
    }

}
