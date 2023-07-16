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
import it.petshop.dto.Elemento;
import it.petshop.dto.Ordine;
import it.petshop.dto.Utente;
import it.petshop.utility.PetShopException;

public class OrdineServlet extends HttpServlet {
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
		int idOrdine = Integer.parseInt(request.getParameter("id"));
		Ordine ordine = ordineDao.retrieveByKey(idOrdine);

		if (!ordineDao.retrieveByUtente(utente).contains(ordine))
			throw new PetShopException("Errore Server: Accesso non consentito", 500);

		List<Elemento> elementi = ordineDao.getElementiOrdine(idOrdine);
		request.setAttribute("elementi", elementi);
		request.setAttribute("numeroProdotti", elementi.stream().mapToInt(Elemento::getQuantita).sum());
		request.setAttribute("totaleOrdine", ordine.getPrezzo());
		request.getRequestDispatcher("/WEB-INF/views/utente/registrato/ordine.jsp").forward(request, response);

	}

}
