package it.petshop.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.petshop.dao.CategoriaDAO;
import it.petshop.model.Categoria;
import it.petshop.model.Prodotto;

/**
 * Servlet implementation class CategoriaServlet
 */

public class CategoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DataSource dataSource;
	private CategoriaDAO categoriaDao;

	@Override
	public void init() throws ServletException {
		dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		categoriaDao = new CategoriaDAO(dataSource);

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String animale = request.getParameter("animale");
		if (animale == null) {
			request.setAttribute("errorMessage", "error");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
			dispatcher.forward(request, response);
		}
		
		String tipologia = Optional.ofNullable(request.getParameter("tipologia")).orElse("");
		Categoria categoria = new Categoria();
		categoria.setAnimale(animale);
		categoria.setTipologia(tipologia);
		
		try {
			
			List<Categoria> categorie = categoriaDao.retrieve(categoria);
			
			System.out.println(animale + tipologia);
			Gson gson = new GsonBuilder().create();

			String json = gson.toJson(
					categorie.stream().map(tipologia.isBlank() ? Categoria::getTipologia : Categoria::getTipologiaIn)
							.collect(Collectors.toSet()));
			System.out.println("aio" + json);
			response.setContentType("application/json");

			response.getWriter().write(json);

		} catch (SQLException e) {
			request.setAttribute("errorMessage", e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
			dispatcher.forward(request, response);
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
