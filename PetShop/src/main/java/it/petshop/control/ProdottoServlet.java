package it.petshop.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.petshop.dao.ProdottoDAO;
import it.petshop.dto.Categoria;
import it.petshop.dto.Prodotto;
import it.petshop.utility.PetShopException;
import it.petshop.utility.Util;


public class ProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DataSource dataSource;
	private ProdottoDAO prodottoDao;

	@Override
	public void init() throws ServletException {
		dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		prodottoDao = new ProdottoDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		Prodotto prodotto = prodottoDao.retrieveByKey(id);
		request.setAttribute("prodotto", prodotto);

		request.getRequestDispatcher("/WEB-INF/views/utente/prodotto.jsp").forward(request, response);
	}
}
