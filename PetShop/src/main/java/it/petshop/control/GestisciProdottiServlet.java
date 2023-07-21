package it.petshop.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import it.petshop.dao.CategoriaDAO;
import it.petshop.dao.ProdottoDAO;
import it.petshop.dto.Categoria;
import it.petshop.dto.Prodotto;
import it.petshop.utility.FileUtil;
import it.petshop.utility.JsonResponseHelper;
import it.petshop.utility.PetShopException;

@MultipartConfig
public class GestisciProdottiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DataSource dataSource;
	private ProdottoDAO prodottoDao;
	private CategoriaDAO categoriaDao;

	@Override
	public void init() throws ServletException {
		DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		prodottoDao = new ProdottoDAO(dataSource);
		categoriaDao = new CategoriaDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 if (request.getPathInfo().equals("/create"))
			 request.getRequestDispatcher("/WEB-INF/views/amministratore/nuovoProdotto.jsp").forward(request, response);
		 else 
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
	    } else if (request.getPathInfo().equals("/create")) {
	        Prodotto newProduct = new Prodotto();
	        newProduct.setNome(getStringFromPart(request.getPart("nome")));
	        newProduct.setDescrizione(getStringFromPart(request.getPart("descrizione")));
	        newProduct.setPrezzo(Double.parseDouble(getStringFromPart(request.getPart("prezzo"))));
	        newProduct.setInMagazzino(Integer.parseInt(getStringFromPart(request.getPart("inMagazzino"))));
	        Categoria categoria = new Categoria();
	        categoria.setAnimale(getStringFromPart(request.getPart("animale")));
	        categoria.setTipologia(getStringFromPart(request.getPart("tipologia")));
	        categoria.setTipologiaIn(getStringFromPart(request.getPart("tipologiaIn")));
	        
	        newProduct.setIdCategoria(categoriaDao.findIdByCategoria(categoria));
	        
	        Part filePart = request.getPart("immagine");
	        ServletContext context = request.getServletContext();
	        String fileName = FileUtil.saveFileToProdottiPath(context, filePart);
	        newProduct.setImmagine(fileName);
	        
	        prodottoDao.save(newProduct);
	        
	        JsonResponseHelper jresponse = new JsonResponseHelper();
	        jresponse.add("status", "success");
	        jresponse.send(response);
	    } else if (request.getPathInfo().equals("/update")) {
	    	
	        int toUpdateId = Integer.parseInt(getStringFromPart(request.getPart("id")));
	        Prodotto productToUpdate = prodottoDao.findById(toUpdateId);

	        productToUpdate.setNome(getStringFromPart(request.getPart("nome")));
	        productToUpdate.setDescrizione(getStringFromPart(request.getPart("descrizione")));
	        productToUpdate.setPrezzo(Double.parseDouble(getStringFromPart(request.getPart("prezzo"))));
	        productToUpdate.setInMagazzino(Integer.parseInt(getStringFromPart(request.getPart("inMagazzino"))));
	        
	        Part filePart = request.getPart("immagine");
	        if (filePart != null) {
	        	ServletContext context = request.getServletContext();
		        String fileName = FileUtil.saveFileToProdottiPath(context, filePart);
		        productToUpdate.setImmagine(fileName);
	        }
	        
	        
	        prodottoDao.updateById(productToUpdate, toUpdateId);
	        
	        JsonResponseHelper jresponse = new JsonResponseHelper();
	        jresponse.add("status", "success");
	        jresponse.send(response);
	    }  else {
	        throw new PetShopException("Operazione Non Definita", HttpServletResponse.SC_NOT_FOUND);
	    }
	}
	
	private String getStringFromPart(Part part) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), StandardCharsets.UTF_8));
	    StringBuilder value = new StringBuilder();
	    char[] buffer = new char[1024];
	    for (int length = 0; (length = reader.read(buffer)) > 0;) {
	        value.append(buffer, 0, length);
	    }
	    return value.toString();
	}	

}
