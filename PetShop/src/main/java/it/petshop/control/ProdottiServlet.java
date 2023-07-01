package it.petshop.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.petshop.dao.CategoriaDAO;
import it.petshop.dao.ProdottoDAO;
import it.petshop.model.Categoria;
import it.petshop.model.Prodotto;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("-");
		request.getParameterNames().asIterator().forEachRemaining(v -> System.out.println(request.getParameter(v)));
		System.out.println("-");
		
		int page = Optional.ofNullable(request.getParameter("page")).map(Integer::parseInt).orElse(1);
		String animale = request.getParameter("animale");
		if (animale == null) {
			request.setAttribute("errorMessage", "error");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
			dispatcher.forward(request, response);
		}
		
		String tipologia = Optional.ofNullable(request.getParameter("tipologia")).orElse("");
		String tipologiaIn = Optional.ofNullable(request.getParameter("tipologiaIn")).orElse("");
		String orderBy = Optional.ofNullable(request.getParameter("order")).orElse("in_magazzino");
		String direction = Optional.ofNullable(request.getParameter("direction")).orElse("asc");
		System.out.println("tipologia " + tipologia);
		Categoria categoria = new Categoria();
		
		categoria.setAnimale(animale);
		categoria.setTipologia(tipologia);
		categoria.setTipologiaIn(tipologiaIn);
		
		
		boolean asc = true;
		if (direction.equals("desc"))
			asc = false;
		else if (!direction.equals("asc")) {
			request.setAttribute("errorMessage", "error");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
			dispatcher.forward(request, response);
		}		
		

		try {
			List<Prodotto> prodotti = prodottoDao.retrieve(categoria, PRODOTTI_PER_PAGINA * (page - 1),
					PRODOTTI_PER_PAGINA, orderBy, asc);
			request.setAttribute("prodotti", prodotti);
			System.out.println("prodotti "+prodotti);

			if (isAjaxRequest(request)) {

				Gson gson = new GsonBuilder().create();

				String json = gson.toJson(prodotti);

				response.setContentType("application/json");

				response.getWriter().write(json);
			} else {

				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/WEB-INF/views/customer/lista-prodotti.jsp");
				dispatcher.forward(request, response);
			}

		} catch (SQLException e) {
			request.setAttribute("errorMessage", e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}
}
