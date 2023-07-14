package it.petshop.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.google.gson.*;

import it.petshop.dao.ProdottoDAO;
import it.petshop.model.*;
import it.petshop.utility.DataHelper;
import it.petshop.utility.Util;;

public class CarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource dataSource;
	private ProdottoDAO prodottoDao;

	@Override
	public void init() throws ServletException {
		dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		prodottoDao = new ProdottoDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (!Util.isAjaxRequest(request))
			request.getRequestDispatcher("/WEB-INF/views/utente/carrello.jsp").forward(request, response);

		HttpSession session = request.getSession(false);
		DataHelper data = new DataHelper();
		Map<Prodotto, Integer> prodotti = getProdottiCarrello(session);
		int numeroProdottiCarrello = 0;
		double totaleCarrello = 0.0;

		if (prodotti != null) {
			List<JsonElement> prodottiCarrello = new ArrayList<>();
			Gson gson = new Gson();
			prodotti.forEach((prodotto, quantita) -> {
				JsonElement element = gson.toJsonTree(prodotto);
				element.getAsJsonObject().addProperty("quantita", quantita);
				prodottiCarrello.add(element);
			});
			
			data.add("prodottiCarrello", prodottiCarrello);
			numeroProdottiCarrello = (int) session.getAttribute("numeroProdottiCarrello");
			totaleCarrello = (double) session.getAttribute("totaleCarrello");
		}

		data.add("numeroProdottiCarrello", numeroProdottiCarrello);
		data.add("totaleCarrello", totaleCarrello);
		data.sendAsJSON(response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		DataHelper status = new DataHelper();
		
		Prodotto prodotto = prodottoDao.retrieveByKey(Integer.parseInt(request.getParameter("id")));
		int quantita = Integer.parseInt(request.getParameter("quantita"));
		String mode = request.getParameter("mode");
		boolean increase = mode.equals("increase") ? true : false;

		Map<Prodotto, Integer> prodotti = getProdottiCarrello(session);
		
		if (prodotti == null)
			prodotti = new LinkedHashMap<>();
			
		int quantitaOld = prodotti.getOrDefault(prodotto, 0);

		prodotti.merge(prodotto, quantita, increase ? (o, n) -> o + n : (o, n) -> n);

		if (prodotti.get(prodotto) > prodotto.getInMagazzino()) {
			prodotti.put(prodotto, quantitaOld);
			status.add("status", "error");
			status.add("message", "Quantità non Disponibile!");
			status.sendAsJSON(response);
			setCarrello(session, prodotti);
			return;
		}

		prodotti.values().removeIf(q -> q == 0);
		
		setCarrello(session, prodotti);
		
		status.add("status", "success");
		status.sendAsJSON(response);
		

	}

	@SuppressWarnings("unchecked")
	private Map<Prodotto, Integer> getProdottiCarrello(HttpSession session) {
		if (session == null)
			return null;

		Object prodottiObj = session.getAttribute("prodottiCarrello");

		if (prodottiObj == null || !(prodottiObj instanceof LinkedHashMap))
			return null;

		return (Map<Prodotto, Integer>) prodottiObj;
	}
	
	private void setCarrello(HttpSession session, Map<Prodotto, Integer> prodotti) {
		int numeroProdottiCarrello = prodotti.values().stream().mapToInt(i -> i.intValue()).reduce(0, (q, r) -> q + r);
		double totaleCarrello = prodotti.entrySet().stream().mapToDouble(c -> c.getKey().getPrezzo() * c.getValue()).reduce(0, (r, p) -> r + p);
		
		DataHelper sessionData = new DataHelper();
		sessionData.add("prodottiCarrello", prodotti);
		sessionData.add("numeroProdottiCarrello", numeroProdottiCarrello);
		sessionData.add("totaleCarrello", Math.round(totaleCarrello * 100.0) / 100.0);
		sessionData.setAsSessionAttribute(session);
	}

}
