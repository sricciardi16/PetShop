package it.petshop.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.google.gson.Gson;

import it.petshop.dao.AmministratoreDAO;
import it.petshop.dao.UtenteDAO;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource dataSource;
	private UtenteDAO utenteDao;
	private AmministratoreDAO amministratoreDao;
	
	@Override
		public void init() throws ServletException {
			dataSource = (DataSource) getServletContext().getAttribute("DataSource");
			utenteDao = new UtenteDAO(dataSource);
			amministratoreDao = new AmministratoreDAO(dataSource);
			
		}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String nomeUtente = (String) session.getAttribute("nomeUtente");
		String role = (String) session.getAttribute("role");
		String dispatch = "/WEB-INF/views/error.jsp";
		if (nomeUtente == null && role == null)
			dispatch = "/WEB-INF/views/login.jsp";
		else if (role.equals("amministratore"))
			dispatch = "/WEB-INF/views/amministratore/dashboard.jsp";
		else if (role.equals("utente"))
			dispatch = "/index.jsp";
		else
			request.setAttribute("error", "errore");
		 
		request.getRequestDispatcher(dispatch).forward(request, response);
	}


	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    
		 String nomeUtente = request.getParameter("nomeUtente");
	       String password = request.getParameter("password");
	       HttpSession session = request.getSession();

	        try {
	        	boolean isUtente = utenteDao.exits(nomeUtente, password);
		        boolean isAmministratore = amministratoreDao.exits(nomeUtente, password);		        
		        
		        Map<String, String> stato = new HashMap<>();
		        
		        if (isUtente) {
		            session.setAttribute("nomeUtente", nomeUtente);
		            session.setAttribute("role", "utente");
		            stato.put("stato", "ok");
		        } else if (isAmministratore) {
		            session.setAttribute("nomeUtente", nomeUtente);
		            session.setAttribute("role", "amministratore");
		            stato.put("stato", "ok");
		        } else {
		        	stato.put("stato", "errore");
		        }
		        
	            String json = new Gson().toJson(stato);
	            response.setContentType("application/json");
	            response.setCharacterEncoding("UTF-8");

	            response.getWriter().write(json);
		        
		        
	        } catch (SQLException e) {
	        	request.setAttribute("errorMessage", e.getMessage());
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
				dispatcher.forward(request, response);
	        }
	        
	        
	        
	    }

}
