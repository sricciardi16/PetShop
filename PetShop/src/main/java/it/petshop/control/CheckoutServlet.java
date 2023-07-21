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
import it.petshop.utility.AjaxUtil;
import it.petshop.utility.FileUtil;
import it.petshop.utility.JsonResponseHelper;

public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String METODO_PAGAMENTO_CONTANTI = "contanti";
	public static final String TOTALE_SA = "totale";
	
	private IndirizzoDAO indirizzoDao;
	private MetodoSpedizioneDAO metodoSpedizioneDao;
	private MetodoPagamentoDAO metodoPagamentoDao;
	private OrdineDAO ordineDao;
	private ElementoDAO elementoDao;
	private ProdottoDAO prodottoDao;

	@Override
	public void init() throws ServletException {
		DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		indirizzoDao = new IndirizzoDAO(dataSource);
		metodoSpedizioneDao = new MetodoSpedizioneDAO(dataSource);
		metodoPagamentoDao = new MetodoPagamentoDAO(dataSource);
		ordineDao = new OrdineDAO(dataSource);
		elementoDao = new ElementoDAO(dataSource);
		prodottoDao = new ProdottoDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		if (AjaxUtil.isAjaxRequest(request)) {
			sendTotaleCheckoutAsJson(response, session);
			return;
		}

		Utente utente = (Utente) session.getAttribute("utente");
		List<Indirizzo> indirizzi = indirizzoDao.findAllByUtente(utente);
		List<MetodoSpedizione> metodiSpedizione = metodoSpedizioneDao.findAll();
		MetodoPagamento contanti = metodoPagamentoDao.findFirstByTipo(METODO_PAGAMENTO_CONTANTI);

		request.setAttribute("indirizzi", indirizzi);
		request.setAttribute("metodiSpedizione", metodiSpedizione);
		request.setAttribute(METODO_PAGAMENTO_CONTANTI, contanti);
		request.getRequestDispatcher("/WEB-INF/views/utente/registrato/checkout.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (AjaxUtil.isAjaxRequest(request)) {
			calculateAndStoreTotaleCheckout(request, session);
			return;
		}

		double prezzo = (double) session.getAttribute(TOTALE_SA);
		int idUtente = ((Utente) session.getAttribute("utente")).getId();
		String metodoPagamentoParam = request.getParameter("metodoPagamento");
		int idMetodoPagamento;
		if ((metodoPagamentoDao.findFirstByTipo(METODO_PAGAMENTO_CONTANTI).getId() + "").equals(metodoPagamentoParam)) {
			idMetodoPagamento = metodoPagamentoDao.findFirstByTipo(METODO_PAGAMENTO_CONTANTI).getId();
		} else {
			MetodoPagamento metodoPagamento = new MetodoPagamento();
			metodoPagamento.setTipo(metodoPagamentoParam);
			idMetodoPagamento = metodoPagamentoDao.save(metodoPagamento);
		}
		int idIndirizzo = Integer.parseInt(request.getParameter("indirizzo"));
		int idMetodoSpedizione = Integer.parseInt(request.getParameter("metodoSpedizione"));
		
		Carrello carrello = (Carrello) session.getAttribute("carrello");
		
		Ordine ordine = new Ordine();
		ordine.setPrezzo(prezzo);
		ordine.setIdUtente(idUtente);
		ordine.setIdMetodoPagamento(idMetodoPagamento);
		ordine.setIdIndirizzo(idIndirizzo);
		ordine.setIdMetodoSpedizione(idMetodoSpedizione);
		
		
		int idOrdine = ordineDao.save(ordine);
		
		
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
			String immagineProdotto = prodotto.getImmagine();
			String formatoImmagine = immagineProdotto.substring(immagineProdotto.lastIndexOf("."));
			String immagineElemento = idElemento + formatoImmagine;
			FileUtil.copyFileFromProdottiToElementiPath(getServletContext(), immagineProdotto, immagineElemento);
			elementoDao.updateImmagineById(idElemento, immagineElemento);
		});

		session.removeAttribute("carrello");
		
		request.setAttribute("status", "success");
		request.setAttribute("message", "Ordine Effettuato!");
		request.getRequestDispatcher("/").forward(request, response);
	}

	private void calculateAndStoreTotaleCheckout(HttpServletRequest request, HttpSession session) {
		int idMetodoSpedizione = Integer.parseInt(request.getParameter("idMetodoSpedizione"));
		double prezzoSpedizione = metodoSpedizioneDao.findByKey(idMetodoSpedizione).getPrezzo();
		double totaleParziale = ((Carrello) session.getAttribute("carrello")).getTotale();
		double totale = Math.round((prezzoSpedizione + totaleParziale) * 100.0) / 100.0;
		
		session.setAttribute(TOTALE_SA, totale);
	}

	private void sendTotaleCheckoutAsJson(HttpServletResponse response, HttpSession session) throws IOException {
		double totale = (double) session.getAttribute(TOTALE_SA);
		
		JsonResponseHelper jresponse = new JsonResponseHelper();
		jresponse.add(TOTALE_SA, totale);
		jresponse.send(response);
	}


}
