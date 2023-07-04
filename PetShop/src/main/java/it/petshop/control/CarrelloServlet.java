package it.petshop.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.petshop.dao.ProdottoDAO;
import it.petshop.model.*;;

/**
 * Servlet implementation class CarrelloServlet
 */

public class CarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Map<Prodotto, Integer> prodotti;   
    private DataSource dataSource;
    private ProdottoDAO prodottoDao;

    @Override
    public void init() throws ServletException {
    	dataSource = (DataSource) getServletContext().getAttribute("DataSource");
    	prodottoDao = new ProdottoDAO(dataSource);
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/customer/carrello.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		System.out.println(session.getId());
		
		Prodotto prodotto;
		try {
			prodotto = prodottoDao.retrieveByKey(Integer.parseInt(request.getParameter("id")));
			int quantita = Integer.parseInt(request.getParameter("quantita"));
			String mode = request.getParameter("mode");
			boolean increase = true; // increase e replace
			if (mode.equals("replace"))
				increase = false;
			
			Object prodottiObj = session.getAttribute("prodottiCarrello");
			if (prodottiObj == null) {
				prodotti = new LinkedHashMap<>();
				prodotti.put(prodotto, quantita);
			} else if (prodottiObj instanceof LinkedHashMap) {
				prodotti = (Map<Prodotto, Integer>) prodottiObj;
				prodotti.merge(prodotto, quantita, increase ? (o,n) -> o + n : (o,n) -> n);
			}
			
			int numeroProdotti = prodotti.values().stream().mapToInt(i -> i.intValue()).reduce(0, (q, r) -> q + r);
			double totale = prodotti.entrySet().stream().mapToDouble(c -> c.getKey().getPrezzo() * c.getValue()).reduce(0, (r, p) -> r + p);
			
			session.setAttribute("prodottiCarrello", prodotti);
			session.setAttribute("numeroProdottiCarrello", numeroProdotti);
			session.setAttribute("totaleCarrello", totale);
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
	}

}
