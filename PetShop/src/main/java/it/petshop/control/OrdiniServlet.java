package it.petshop.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.petshop.dao.OrdineDAO;
import it.petshop.model.Indirizzo;
import it.petshop.model.Ordine;
import it.petshop.model.Utente;
import it.petshop.utility.PetShopException;

public class OrdiniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DataSource dataSource;
	private OrdineDAO ordineDao;

	@Override
	public void init() throws ServletException {
		dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		ordineDao = new OrdineDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		Utente utente = (Utente) session.getAttribute("utente");
		List<Ordine> ordini = ordineDao.retrieveByUtente(utente);
		request.setAttribute("ordini", ordini);
		request.getRequestDispatcher("/WEB-INF/views/utente/registrato/ordini.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
