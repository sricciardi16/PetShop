package it.petshop.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.petshop.dao.UtenteDAO;
import it.petshop.dto.Utente;
import it.petshop.utility.PetShopException;
import it.petshop.utility.RedirectUtil;

public class RegistrazioneServlet extends HttpServlet {

	private UtenteDAO utenteDao;
	private UtenteDAO amministratoreDao;

	@Override
	public void init() throws ServletException {
		DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		utenteDao = new UtenteDAO(dataSource);
		amministratoreDao = new UtenteDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/utente/registrazione.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String email = request.getParameter("email");
		String nomeUtente = request.getParameter("nomeUtente");
		String password = request.getParameter("password");
		String telefono = request.getParameter("telefono");

		Utente utente = new Utente();
		utente.setNome(nome);
		utente.setCognome(cognome);
		utente.setEmail(email);
		utente.setNomeUtente(nomeUtente);
		utente.setPassword(password);
		utente.setTelefono(telefono);

		// ---
		if (utenteDao.findByNomeUtente(nomeUtente) != amministratoreDao.findByNomeUtente(nomeUtente))
			throw new PetShopException("Nome Utente Gia' Esistente", HttpServletResponse.SC_CONFLICT);

		utenteDao.save(utente);
		// ---

		request.setAttribute("status", "success");
		request.setAttribute("message", "Registrazione Effettuata!");

		if (!RedirectUtil.redirectToStoredUrl(session, response))
			response.sendRedirect(request.getContextPath() + "/");

	}

}
