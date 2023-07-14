package it.petshop.control;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.petshop.dao.CategoriaDAO;
import it.petshop.model.Categoria;
import it.petshop.utility.DataHelper;
import it.petshop.utility.PetShopException;

public class CategoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DataSource dataSource;
	private CategoriaDAO categoriaDao;

	@Override
	public void init() throws ServletException {
		dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		categoriaDao = new CategoriaDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String animale = request.getParameter("animale");
		if (animale == null)
			throw new PetShopException("Errore Server");

		String tipologia = Optional.ofNullable(request.getParameter("tipologia")).orElse("");
		Categoria categoria = new Categoria();
		categoria.setAnimale(animale);
		categoria.setTipologia(tipologia);

		List<Categoria> categorie = categoriaDao.retrieve(categoria);

		DataHelper data = new DataHelper();
		Set<String> tipologie = categorie.stream().map(tipologia.isBlank() ? Categoria::getTipologia : Categoria::getTipologiaIn).collect(Collectors.toSet());
		data.add("tipologie", tipologie);
		data.sendAsJSON(response);
	}
}
