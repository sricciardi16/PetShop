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
import it.petshop.dto.Amministratore;
import it.petshop.dto.Utente;
import it.petshop.utility.JsonResponseHelper;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource dataSource;
	private UtenteDAO utenteDao;
	private AmministratoreDAO amministratoreDao;

	@Override
	public void init() throws ServletException {
		DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");
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

		// ---
		Utente utente = utenteDao.findByNomeUtenteAndPassword(nomeUtente, password);
		Amministratore amministratore = amministratoreDao.findByNomeUtenteAndPassword(nomeUtente, password);
		String whoFound = utente != null ? "utente" : amministratore != null ? "amministratore" : null;
		Object foundObj = utente != null ?  utente  : amministratore != null ?  amministratore  : null;
		// ---

		JsonResponseHelper jresponse = new JsonResponseHelper();

		if (whoFound == null) {
			jresponse.add("status", "error");
			jresponse.add("message", "Errore. Credenziali non valide!");
		} else {
			session.setAttribute("nomeUtente", nomeUtente);
			session.setAttribute(whoFound, foundObj);
			jresponse.add("status", "success");
		}
		jresponse.send(response);

	}
}
