package it.petshop.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.petshop.dao.AmministratoreDAO;
import it.petshop.dao.UtenteDAO;
import it.petshop.utility.DataHelper;


public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession(false).invalidate();
		DataHelper data = new DataHelper();
		data.add("status", "success");
		data.add("message", "Logout effettuato");
		data.setAsRequestAttribute(request);
		request.getRequestDispatcher("").forward(request, response);
	}

}
