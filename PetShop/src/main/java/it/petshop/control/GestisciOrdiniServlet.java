package it.petshop.control;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.petshop.dao.MetodoPagamentoDAO;
import it.petshop.dao.OrdineDAO;
import it.petshop.dao.UtenteDAO;
import it.petshop.dto.Ordine;
import it.petshop.dto.Utente;
import it.petshop.utility.AjaxUtil;
import it.petshop.utility.JsonResponseHelper;
import it.petshop.utility.PetShopException;

public class GestisciOrdiniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private OrdineDAO ordineDao;
	private UtenteDAO utenteDao;
	private MetodoPagamentoDAO metodoPagamentoDao;

	@Override
	public void init() throws ServletException {
		DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		ordineDao = new OrdineDAO(dataSource);
		utenteDao = new UtenteDAO(dataSource);
		metodoPagamentoDao = new MetodoPagamentoDAO(dataSource);
	}


	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!AjaxUtil.isAjaxRequest(request)) {
	        request.getRequestDispatcher("/WEB-INF/views/amministratore/gestisciOrdini.jsp").forward(request, response);
	        return;
		}
		
		if (request.getPathInfo() != null && request.getPathInfo().equals("/utenti")) {
			List<Utente> utenti = utenteDao.findAll();
			
			JsonResponseHelper jresponse = new JsonResponseHelper();
			jresponse.add("clienti", utenti);
			jresponse.send(response);
			return;
			
		} 
		
		 
		Utente utente = request.getParameter("clienteId") == null || request.getParameter("clienteId").isBlank() ? null : new Utente(Integer.parseInt(request.getParameter("clienteId")));
		 String startDate = request.getParameter("startDate");
	     String endDate = request.getParameter("endDate");
	     String ordinamento = request.getParameter("ordinamento");
	     String ordine = request.getParameter("ordine");
	     
	     
	     if (!Arrays.asList("data_ora", "prezzo", null).contains(ordinamento) || !Arrays.asList("asc", "desc", null).contains(ordine))
	    	 throw new PetShopException("Errore Parametri", 500);
	     
		List<Ordine> ordini = ordineDao.findAllByUtenteDataSorted(utente, startDate, endDate, ordinamento, ordine);
		
		Gson gson = new Gson();
		
		Function<Ordine, JsonElement> ordineConClienteEMetodoPagamento = o -> {
			JsonElement element = gson.toJsonTree(o);
			JsonObject jobj = element.getAsJsonObject();
			jobj.addProperty("nomeUtente", utenteDao.findById(o.getIdUtente()).getNomeUtente());
			jobj.addProperty("nome", utenteDao.findById(o.getIdUtente()).getNome());
			jobj.addProperty("cognome", utenteDao.findById(o.getIdUtente()).getCognome());
			jobj.addProperty("metodoPagamento", metodoPagamentoDao.findById(o.getIdMetodoPagamento()).getTipo());
			return element;
		};
		
		List<JsonElement> jordini = ordini.stream().map(ordineConClienteEMetodoPagamento).collect(Collectors.toList());
		
		
		JsonResponseHelper jresponse = new JsonResponseHelper();
		jresponse.add("ordini", jordini);
		jresponse.send(response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int idOrdine = Integer.parseInt(request.getParameter("id"));
		String stato = request.getParameter("stato");
		
		boolean updated = ordineDao.updateStatoById(idOrdine, stato);
		
		JsonResponseHelper jresponse = new JsonResponseHelper();
		if (updated) {
			jresponse.add("status", "success");
			jresponse.add("message", "Stato Ordine Aggiornato");
		} else {
			jresponse.add("status", "error");
			jresponse.add("message", "Errore Stato Ordine");
		}
		jresponse.send(response);	
		
	}

}
