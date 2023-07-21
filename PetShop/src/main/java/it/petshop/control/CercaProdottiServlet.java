package it.petshop.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.petshop.dao.ProdottoDAO;
import it.petshop.dto.Prodotto;
import it.petshop.utility.JsonResponseHelper;

public class CercaProdottiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final int LIMIT = 5;
	
	private DataSource dataSource;
	private ProdottoDAO prodottoDao;

	@Override
	public void init() throws ServletException {
		dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		prodottoDao = new ProdottoDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String like = request.getParameter("like");
		List<Prodotto> prodotti = prodottoDao.findFirstLimitByNomeLike(like, LIMIT);

		JsonResponseHelper jresponse = new JsonResponseHelper();
		jresponse.add("prodotti", prodotti);
		jresponse.send(response);
		

	}
}
