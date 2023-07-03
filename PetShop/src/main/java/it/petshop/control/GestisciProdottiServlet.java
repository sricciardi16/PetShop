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

import it.petshop.model.Categoria;
import it.petshop.model.Prodotto;
import it.petshop.dao.CategoriaDAO;
import it.petshop.dao.ProdottoDAO;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Prodotto> prodotti;
		try {
			prodotti = prodottoDao.retrieveAll();

			Function<Prodotto, Categoria> getCategoria = p -> {
				try {
					return categoriaDao.retrieveByKey(p.getIdCategoria());
				} catch (SQLException e) {

				}
				return null;
			};

			Map<Prodotto, Categoria> prodottiConCategoria = prodotti.stream().collect(Collectors.toMap(p -> p,
					getCategoria, (o, n) -> o, () -> new TreeMap<>((p, v) -> v.getId() - p.getId())));

			request.setAttribute("prodottiConCategoria", prodottiConCategoria);
			request.getRequestDispatcher("/WEB-INF/views/amministratore/prodotti.jsp").forward(request, response);
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
