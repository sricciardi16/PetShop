package it.petshop.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.google.gson.*;

import it.petshop.dao.ProdottoDAO;
import it.petshop.dto.*;
import it.petshop.utility.JsonResponseHelper;
import it.petshop.utility.Util;

public class CarrelloNew extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource dataSource;
	private ProdottoDAO prodottoDao;

	@Override
	public void init() throws ServletException {
		dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		prodottoDao = new ProdottoDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (!Util.isAjaxRequest(request)) {
			request.getRequestDispatcher("/WEB-INF/views/utente/carrello.jsp").forward(request, response);
			return;
		}
			
		HttpSession session = request.getSession(false);
		Carrello carrello = (Carrello) session.getAttribute("carrello");
		JsonResponseHelper jresponse = new JsonResponseHelper();

		if (carrello != null) {
			Gson gson = new Gson();
			JsonElement carrelloJson = gson.toJsonTree(carrello);
			jresponse.add("carrello", carrelloJson);
		}

		jresponse.send(response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		JsonResponseHelper jresponse = new JsonResponseHelper();

		Prodotto prodotto = prodottoDao.findById(Integer.parseInt(request.getParameter("id")));
		int quantita = Integer.parseInt(request.getParameter("quantita"));
		String mode = request.getParameter("mode");
		boolean increase = mode.equals("increase") ? true : false;

		Carrello carrello = (Carrello) session.getAttribute("carrello");
		if (carrello == null) {
			carrello = new Carrello();
			session.setAttribute("carrello", carrello);
		}

		int quantitaOld = carrello.getProdotti().getOrDefault(prodotto, 0);

		carrello.getProdotti().merge(prodotto, quantita, increase ? (o, n) -> o + n : (o, n) -> n);

		if (carrello.getProdotti().get(prodotto) > prodotto.getInMagazzino()) {
			carrello.getProdotti().put(prodotto, quantitaOld);
			jresponse.add("status", "error");
			jresponse.add("message", "Quantità non Disponibile!");
			jresponse.send(response);
			return;
		}

		carrello.getProdotti().values().removeIf(q -> q == 0);

		int numeroProdottiCarrello = carrello.getProdotti().values().stream().mapToInt(i -> i.intValue()).reduce(0, (q, r) -> q + r);
		double totaleCarrello = carrello.getProdotti().entrySet().stream().mapToDouble(c -> c.getKey().getPrezzo() * c.getValue()).reduce(0, (r, p) -> r + p);

		carrello.setNumeroProdotti(numeroProdottiCarrello);
		carrello.setTotale(Math.round(totaleCarrello * 100.0) / 100.0);

		jresponse.add("status", "success");
		jresponse.send(response);
	}
}
