package it.petshop.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.petshop.dao.IndirizzoDAO;
import it.petshop.dto.Indirizzo;
import it.petshop.dto.Utente;
import it.petshop.utility.PetShopException;

public class IndirizziServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DataSource dataSource;
	private IndirizzoDAO indirizzoDao;

	@Override
	public void init() throws ServletException {
		dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		indirizzoDao = new IndirizzoDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getPathInfo() != null && !request.getPathInfo().equals("/new"))
			throw new PetShopException("Percorso Errato", 404);

		boolean create = request.getPathInfo() != null ? true : false;

		if (create) {
			String redirect = request.getParameter("redirect");
			if (redirect != null && !redirect.equals("checkout"))
				throw new PetShopException("Errore Server: Pagina Non Esistente", 404);
			redirect = redirect == null ? "user/indirizzi" : "user/checkout";
			request.getSession(false).setAttribute("redirect", redirect);
			request.getRequestDispatcher("/WEB-INF/views/utente/registrato/nuovoIndirizzo.jsp").forward(request, response);
		} else {
			HttpSession session = request.getSession(false);
			Utente utente = (Utente) session.getAttribute("utente");
			List<Indirizzo> indirizzi = indirizzoDao.retrieveByUtente(utente);
			request.setAttribute("indirizzi", indirizzi);
			request.getRequestDispatcher("/WEB-INF/views/utente/registrato/indirizzi.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utente utente = (Utente) session.getAttribute("utente");

		if (request.getPathInfo().equals("/new")) {
			String alias = request.getParameter("alias");
			String via = request.getParameter("via");
			String numero = request.getParameter("numero");
			String citta = request.getParameter("citta");
			String codicePostale = request.getParameter("codicePostale");
			String provincia = request.getParameter("provincia");
			String paese = request.getParameter("paese");
			
			Indirizzo indirizzo = new Indirizzo();
			indirizzo.setAlias(alias);
			indirizzo.setVia(via);
			indirizzo.setNumero(numero);
			indirizzo.setCitta(citta);
			indirizzo.setCodicePostale(codicePostale);
			indirizzo.setProvincia(provincia);
			indirizzo.setPaese(paese);
			indirizzo.setIdUtente(utente.getId());

			indirizzoDao.create(indirizzo);
			
			response.sendRedirect(request.getContextPath() + "/" + request.getSession(false).getAttribute("redirect"));
			request.getSession().removeAttribute("redirect");
		} else if (request.getPathInfo().equals("/delete")) {
			indirizzoDao.delete(Integer.parseInt(request.getParameter("id")));
			response.sendRedirect(request.getContextPath() + "/user/indirizzi");
		} else {
			throw new PetShopException("Errore Server", 500);
		}
	}
}
