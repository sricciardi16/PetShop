package it.petshop.control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.petshop.dao.ElementoDAO;
import it.petshop.dao.IndirizzoDAO;
import it.petshop.dao.MetodoPagamentoDAO;
import it.petshop.dao.MetodoSpedizioneDAO;
import it.petshop.dao.OrdineDAO;
import it.petshop.dao.ProdottoDAO;
import it.petshop.dto.Carrello;
import it.petshop.dto.Elemento;
import it.petshop.dto.Indirizzo;
import it.petshop.dto.MetodoPagamento;
import it.petshop.dto.MetodoSpedizione;
import it.petshop.dto.Ordine;
import it.petshop.dto.Utente;
import it.petshop.utility.JsonResponseHelper;
import it.petshop.utility.PetShopException;
import it.petshop.utility.Util;

public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DataSource dataSource;
	private IndirizzoDAO indirizzoDao;
	private MetodoSpedizioneDAO metodoSpedizioneDao;
	private MetodoPagamentoDAO metodoPagamentoDao;
	private OrdineDAO ordineDao;
	private ElementoDAO elementoDao;
	private ProdottoDAO prodottoDao;

	@Override
	public void init() throws ServletException {
		dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		indirizzoDao = new IndirizzoDAO(dataSource);
		metodoSpedizioneDao = new MetodoSpedizioneDAO(dataSource);
		metodoPagamentoDao = new MetodoPagamentoDAO(dataSource);
		ordineDao = new OrdineDAO(dataSource);
		elementoDao = new ElementoDAO(dataSource);
		prodottoDao = new ProdottoDAO(dataSource);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (Util.isAjaxRequest(request)) {
			getTotaleJSon(response, session);
			return;
		}

		Utente utente = (Utente) session.getAttribute("utente");
		List<Indirizzo> indirizzi = indirizzoDao.findAllByUtente(utente);
		List<MetodoSpedizione> metodiSpedizione = metodoSpedizioneDao.findAll();
		MetodoPagamento contanti = metodoPagamentoDao.findFirstByTipo("contanti");
		JsonResponseHelper data= new JsonResponseHelper();
		request.setAttribute("indirizzi", indirizzi);
		request.setAttribute("metodiSpedizione", metodiSpedizione);
		request.setAttribute("contanti", contanti);
		request.getRequestDispatcher("/WEB-INF/views/utente/registrato/checkout.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (Util.isAjaxRequest(request)) {
			setTotale(request, session);
			return;
		}

		double prezzo = (double) session.getAttribute("totale");
		int idUtente = ((Utente) session.getAttribute("utente")).getId();
		String metodoPagamentoParam = request.getParameter("metodoPagamento");
		int idMetodoPagamento;
		if ((metodoPagamentoDao.findFirstByTipo("contanti").getId() + "").equals(metodoPagamentoParam)) {
			idMetodoPagamento = metodoPagamentoDao.findFirstByTipo("contanti").getId();
		} else {
			MetodoPagamento metodoPagamento = new MetodoPagamento();
			metodoPagamento.setTipo(metodoPagamentoParam);
			idMetodoPagamento = metodoPagamentoDao.save(metodoPagamento);
		}
		int idIndirizzo = Integer.parseInt(request.getParameter("indirizzo"));
		int idMetodoSpedizione = Integer.parseInt(request.getParameter("metodoSpedizione"));

		Ordine ordine = new Ordine();
		ordine.setPrezzo(prezzo);
		ordine.setIdUtente(idUtente);
		ordine.setIdMetodoPagamento(idMetodoPagamento);
		ordine.setIdIndirizzo(idIndirizzo);
		ordine.setIdMetodoSpedizione(idMetodoSpedizione);
		int idOrdine = ordineDao.save(ordine);

		Carrello carrello = (Carrello) session.getAttribute("carrello");
		
		carrello.getProdotti().forEach((prodotto, quantita) -> {
			Elemento elemento = new Elemento();
			elemento.setNome(prodotto.getNome());
			elemento.setDescrizione(prodotto.getDescrizione());
			elemento.setPrezzo(prodotto.getPrezzo());
			elemento.setIdProdotto(prodotto.getId());
			elemento.setQuantita(quantita);
			prodottoDao.updateInMagazzino(prodotto.getId(), quantita);
			elemento.setIdOrdine(idOrdine);
			int idElemento = elementoDao.save(elemento);
			String nomeImmagine = idElemento + ".jpg";
			salvaImmagine(prodotto.getImmagine(), nomeImmagine);
			elementoDao.updateImmagineById(idElemento, nomeImmagine);
		});

		session.removeAttribute("carrello");

		JsonResponseHelper message = new JsonResponseHelper();
		request.setAttribute("status", "success");
		request.setAttribute("message", "Ordine Effettuato!");
		request.getRequestDispatcher("/").forward(request, response);
	}

	private void setTotale(HttpServletRequest request, HttpSession session) {
		int idMetodoSpedizione = Integer.parseInt(request.getParameter("idMetodoSpedizione"));
		double prezzoSpedizione = metodoSpedizioneDao.findByKey(idMetodoSpedizione).getPrezzo();
		double totaleParziale = ((Carrello) session.getAttribute("carrello")).getTotale();
		session.setAttribute("totale", Math.round((prezzoSpedizione + totaleParziale) * 100.0) / 100.0);
	}

	private void getTotaleJSon(HttpServletResponse response, HttpSession session) throws IOException {
		JsonResponseHelper data = new JsonResponseHelper();
		double totale = (double) session.getAttribute("totale");
		data.add("totale", totale);
		data.send(response);
	}

	private void salvaImmagine(String prodotto, String elemento) {
		Path origine = Path.of(getServletContext().getRealPath(getServletContext().getInitParameter("imgProdottiPath")), prodotto);
		Path destinazione = Path.of(getServletContext().getRealPath(getServletContext().getInitParameter("imgElementiPath")), elemento);
		try {
			Files.copy(origine, destinazione, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new PetShopException("Errore I/O: impossibile salvere immagine", 500, e);
		}
	}

}
