package it.petshop.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.petshop.dao.ElementoDAO;
import it.petshop.dao.OrdineDAO;
import it.petshop.dto.Elemento;
import it.petshop.dto.Ordine;
import it.petshop.dto.Utente;
import it.petshop.utility.PetShopException;

public class OrdineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private OrdineDAO ordineDao;
	private ElementoDAO elementoDao;

	@Override
	public void init() throws ServletException {
		DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		ordineDao = new OrdineDAO(dataSource);
		elementoDao = new ElementoDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Utente utente = (Utente) session.getAttribute("utente");
		int idOrdine = Integer.parseInt(request.getParameter("id"));
		
		// ---
		Ordine ordine = ordineDao.findById(idOrdine);


        if (request.getServletPath().equals("/utente/ordine")) {
        	if (!ordineDao.findAllByUtente(utente).contains(ordine)) {
        		throw new PetShopException("Accesso Negato Alla Risorsa", HttpServletResponse.SC_FORBIDDEN);
        	}
        }
		
		List<Elemento> elementi = elementoDao.findAllByIdOrdine(idOrdine);
		int numeroProdotti = elementi.stream().mapToInt(Elemento::getQuantita).sum();
		double totaleOrdine = ordine.getPrezzo();
		// ---
		
		request.setAttribute("elementi", elementi);
		request.setAttribute("numeroProdotti", numeroProdotti);
		request.setAttribute("totaleOrdine", totaleOrdine);
		request.getRequestDispatcher("/WEB-INF/views/utente/registrato/ordine.jsp").forward(request, response);

	}

}
