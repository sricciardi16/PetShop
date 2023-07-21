package it.petshop.control;

import java.io.IOException;
import java.util.LinkedHashSet;
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
import it.petshop.dto.Categoria;
import it.petshop.utility.JsonResponseHelper;
import it.petshop.utility.PetShopException;

public class CategoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DataSource dataSource;
	private CategoriaDAO categoriaDao;

	@Override
	public void init() throws ServletException {
		DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		categoriaDao = new CategoriaDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String animale = request.getParameter("animale");
		
		if (animale == null || (!animale.equals("cane") && !animale.equals("gatto")))
			throw new PetShopException("Solo Cani e Gatti: Errore nei Parametri", 404);

		String tipologia = Optional.ofNullable(request.getParameter("tipologia")).orElse("");
		
		Categoria categoria = new Categoria();
		categoria.setAnimale(animale);
		categoria.setTipologia(tipologia);
		
		// ---
		List<Categoria> categorie = categoriaDao.findAllByCategoria(categoria);
		Set<String> tipologie = categorie.stream().map(tipologia.isBlank() ? Categoria::getTipologia : Categoria::getTipologiaIn).collect(Collectors.toCollection(LinkedHashSet::new));
		// ---
		
		JsonResponseHelper jresponse = new JsonResponseHelper();
		jresponse.add("tipologie", tipologie);
		jresponse.send(response);
	}
}
