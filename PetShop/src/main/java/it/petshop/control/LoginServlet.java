package it.petshop.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.petshop.dao.AmministratoreDAO;
import it.petshop.dao.UtenteDAO;
import it.petshop.utility.DataHelper;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource dataSource;
	private UtenteDAO utenteDao;
	private AmministratoreDAO amministratoreDao;

	@Override
	public void init() throws ServletException {
		dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		utenteDao = new UtenteDAO(dataSource);
		amministratoreDao = new AmministratoreDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeUtente = request.getParameter("nomeUtente");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();

		boolean isUtente = utenteDao.exists(nomeUtente, password);
		boolean isAmministratore = amministratoreDao.exists(nomeUtente, password);

		DataHelper data = new DataHelper();

		if (isUtente) {
			session.setAttribute("nomeUtente", nomeUtente);
			session.setAttribute("utente", utenteDao.retrieveByNomeUtente(nomeUtente));
			data.add("status", "success");
			data.add("message", "Ciao " + nomeUtente);
			data.sendAsJSON(response);;
		} else if (isAmministratore) {
			session.setAttribute("nomeUtente", nomeUtente);
			session.setAttribute("amministratore", amministratoreDao.retrieveByNomeUtente(nomeUtente));
			data.add("status", "success");
			data.add("message", "Ciao " + nomeUtente + ". Sei un Amministratore.");
			data.sendAsJSON(response);
		} else {
			data.add("status", "error");
			data.add("message", "Errore. Credenziali non valide!");
			data.sendAsJSON(response);
		}

		
	}
}
