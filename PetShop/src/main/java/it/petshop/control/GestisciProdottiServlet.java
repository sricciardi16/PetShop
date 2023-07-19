package it.petshop.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.petshop.dao.CategoriaDAO;
import it.petshop.dao.ProdottoDAO;
import it.petshop.dto.Categoria;
import it.petshop.dto.Prodotto;
import it.petshop.utility.JsonResponseHelper;
import it.petshop.utility.PetShopException;

/**
 * Servlet implementation class GestisciProdotti
 */

public class GestisciProdottiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DataSource dataSource;
	private ProdottoDAO prodottoDao;
	private CategoriaDAO categoriaDao;

	@Override
	public void init() throws ServletException {
		dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		prodottoDao = new ProdottoDAO(dataSource);
		categoriaDao = new CategoriaDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/amministratore/gestisciProdotti.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getPathInfo().equals("/delete")) {
			int toDeleteId = Integer.parseInt(request.getParameter("id"));
		
			// ---
			prodottoDao.deleteById(toDeleteId);
			// ---
			
			JsonResponseHelper jresponse = new JsonResponseHelper();
			jresponse.add("status", "success");
			jresponse.send(response);
			
			

		} else {
			throw new PetShopException("Operazione Non Definita", HttpServletResponse.SC_NOT_FOUND);
		}
	}

}
