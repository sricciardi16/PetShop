package it.petshop.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.petshop.dao.AmministratoreDAO;
import it.petshop.dao.UtenteDAO;
import it.petshop.dto.Utente;

public class RegistrazioneServlet extends HttpServlet {
	private DataSource dataSource;
	private UtenteDAO utenteDao;

	@Override
	public void init() throws ServletException {
		dataSource = (DataSource) getServletContext().getAttribute("DataSource");
		utenteDao = new UtenteDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/utente/registrazione.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Aggiugnere verifica nomeutente univoco
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

		utenteDao.create(utente);
		response.sendRedirect(request.getContextPath());
		// continuare qualcosa per vedere se andato a buon fine e redirect
	}

}
