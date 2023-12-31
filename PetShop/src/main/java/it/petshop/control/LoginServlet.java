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
import it.petshop.utility.RedirectUtil;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UtenteDAO utenteDao;
	private AmministratoreDAO amministratoreDao;

	@Override
	public void init() throws ServletException {
		DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		utenteDao = new UtenteDAO(dataSource);
		amministratoreDao = new AmministratoreDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RedirectUtil.storeCurrentUrlForRedirect(request);
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeUtente = request.getParameter("nomeUtente");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();

		// ---
		Utente utente = utenteDao.findByNomeUtenteAndPassword(nomeUtente, password);
		Amministratore amministratore = amministratoreDao.findByNomeUtenteAndPassword(nomeUtente, password);
		String whoFound = null;
		if (utente != null)
			whoFound = "utente";
		else if (amministratore != null)
			whoFound = "amministratore";
		Object foundObj = null;
		if (utente != null)
			foundObj = utente;
		else if (amministratore != null)
			foundObj = amministratore;
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
