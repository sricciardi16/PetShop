package it.petshop.control;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.catalina.connector.Response;

import it.petshop.dao.ProdottoDAO;
import it.petshop.dto.Categoria;
import it.petshop.dto.Prodotto;
import it.petshop.utility.JsonResponseHelper;
import it.petshop.utility.PetShopException;
import it.petshop.utility.AjaxUtil;

public class ProdottiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final int PRODOTTI_PER_PAGINA = 12;

	private DataSource dataSource;
	private ProdottoDAO prodottoDao;

	@Override
	public void init() throws ServletException {
		dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		prodottoDao = new ProdottoDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int page = Optional.ofNullable(request.getParameter("page")).map(Integer::parseInt).orElse(1);
		String animale = request.getParameter("animale");
		String tipologia = Optional.ofNullable(request.getParameter("tipologia")).orElse("");
		String tipologiaIn = Optional.ofNullable(request.getParameter("tipologiaIn")).orElse("");
		String orderBy = Optional.ofNullable(request.getParameter("order")).orElse("in_magazzino");
		String direction = Optional.ofNullable(request.getParameter("direction")).orElse("asc");

		if ((animale == null || (!animale.equals("cane") && !animale.equals("gatto"))) && (direction == null || direction.equals("asc")))
			throw new PetShopException("Parametri Errati nella Richiesta dei Prodotti", 404);

		if (!AjaxUtil.isAjaxRequest(request)) {
			if (request.getSession(false) != null && request.getSession(false).getAttribute("amministratore") != null )
				request.getRequestDispatcher("/WEB-INF/views/amministratore/gestisciProdotti.jsp").forward(request, response);
			else 
				request.getRequestDispatcher("/WEB-INF/views/utente/listaProdotti.jsp").forward(request, response);
			return;
		} 

		Categoria categoria = new Categoria();
		categoria.setAnimale(animale);
		categoria.setTipologia(tipologia);
		categoria.setTipologiaIn(tipologiaIn);

		boolean asc = true;
		if (direction == null || direction.isEmpty() || direction.equals("asc"))
			asc = true;
		else if (direction.equals("desc"))
			asc = false;
		else
			throw new PetShopException("Errore Parametri", HttpServletResponse.SC_NOT_FOUND);

		// ---
		int limit = PRODOTTI_PER_PAGINA;
		int offset = PRODOTTI_PER_PAGINA * (page - 1);
		List<Prodotto> prodotti = prodottoDao.findAllByCategoriaWithLimit(categoria, limit, offset, orderBy, asc);
		int numeroPagine = prodottoDao.countPagine(categoria, PRODOTTI_PER_PAGINA);
		// - - -

		JsonResponseHelper jresponse = new JsonResponseHelper();
		jresponse.add("prodotti", prodotti);
		jresponse.add("numeroPagine", numeroPagine);
		jresponse.send(response);

	}
	
	
	
	
}