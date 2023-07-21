package it.petshop.control;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import it.petshop.dao.ProdottoDAO;
import it.petshop.dto.Carrello;
import it.petshop.dto.Prodotto;
import it.petshop.utility.AjaxUtil;
import it.petshop.utility.JsonResponseHelper;

public class CarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProdottoDAO prodottoDao;

	@Override
	public void init() throws ServletException {
		DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		prodottoDao = new ProdottoDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (!AjaxUtil.isAjaxRequest(request)) {
			request.getRequestDispatcher("/WEB-INF/views/utente/carrello.jsp").forward(request, response);
			return;
		}

		HttpSession session = request.getSession(false);

		Carrello carrello = session != null ? Optional.ofNullable((Carrello) session.getAttribute("carrello")).orElse(new Carrello()) : new Carrello();
		
		Gson gson = new Gson();
		
		Function<Map.Entry<Prodotto, Integer>, JsonElement> prodottoConQuantita = entry -> {
			Prodotto prodotto = entry.getKey();
			Integer quantita = entry.getValue();
			JsonElement element = gson.toJsonTree(prodotto);
			element.getAsJsonObject().addProperty("quantita", quantita);
			return element;
		};
		
		List<JsonElement> prodottiCarrello = 
				carrello.getProdotti().entrySet().stream().
				map(prodottoConQuantita).
				collect(Collectors.toList());
		
		
		JsonResponseHelper jresponse = new JsonResponseHelper();
		
		jresponse.add("prodottiCarrello", prodottiCarrello);
		jresponse.add("numeroProdottiCarrello", carrello.getNumeroProdotti());
		jresponse.add("totaleCarrello", carrello.getTotale());

		jresponse.send(response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		Prodotto prodotto = prodottoDao.findById(Integer.parseInt(request.getParameter("id")));
		int quantita = Integer.parseInt(request.getParameter("quantita"));
		String mode = request.getParameter("mode");
		boolean increase = mode.equals("increase") ? true : false;

		Carrello carrello = session != null ? Optional.ofNullable((Carrello) session.getAttribute("carrello")).orElse(new Carrello()) : new Carrello();

		// --- 
		int quantitaOld = carrello.getProdotti().getOrDefault(prodotto, 0);

		carrello.getProdotti().merge(prodotto, quantita, increase ? (o, n) -> o + n : (o, n) -> n);

		boolean quantitaDisponibile = true;
		
		if (carrello.getProdotti().get(prodotto) > prodotto.getInMagazzino()) {
			carrello.getProdotti().put(prodotto, quantitaOld);
			quantitaDisponibile = false;
		}
		
		carrello.getProdotti().values().removeIf(q -> q == 0);
		
		int numeroProdottiCarrello = carrello.getProdotti().values().stream().mapToInt(i -> i.intValue()).reduce(0, (q, r) -> q + r);
		double totaleCarrello = carrello.getProdotti().entrySet().stream().mapToDouble(c -> c.getKey().getPrezzo() * c.getValue()).reduce(0, (r, p) -> r + p);
		totaleCarrello = Math.round(totaleCarrello * 100.0) / 100.0;
		
		carrello.setNumeroProdotti(numeroProdottiCarrello);
		carrello.setTotale(totaleCarrello);
		// ---
		
		session.setAttribute("carrello", carrello);
		JsonResponseHelper jresponse = new JsonResponseHelper();
		if (quantitaDisponibile) {
			jresponse.add("status", "success");
		} else {
			jresponse.add("status", "error");
			jresponse.add("message", "Quantità non Disponibile!");
		}
		jresponse.send(response);

	}
}
