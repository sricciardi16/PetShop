package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.model.Utente;

public class UtenteDAO implements DAO<Utente> {

	private DataSource dataSource;
	private static final String TABLE_NAME = "utente";

	public UtenteDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public synchronized void create(Utente utente) throws SQLException {
		String insertSQL = "INSERT INTO " + TABLE_NAME
				+ " (id, nome_utente, password, nome, cognome, email, telefono) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setInt(1, utente.getId());
			preparedStatement.setString(2, utente.getNomeUtente());
			preparedStatement.setString(3, utente.getPassword());
			preparedStatement.setString(4, utente.getNome());
			preparedStatement.setString(5, utente.getCognome());
			preparedStatement.setString(6, utente.getEmail());
			preparedStatement.setString(7, utente.getTelefono());

			preparedStatement.executeUpdate();
		}
	}

	@Override
	public synchronized boolean delete(int id) throws SQLException {
		int result = 0;
		String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeUpdate();
		}

		return result != 0;
	}

	@Override
	public synchronized List<Utente> retrieveAll(String order) throws SQLException {
		List<Utente> utenti = new ArrayList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
				ResultSet rs = preparedStatement.executeQuery()) {

			while (rs.next()) {
				Utente bean = new Utente();
				bean.setId(rs.getInt("id"));
				bean.setNomeUtente(rs.getString("nome_utente"));
				bean.setPassword(rs.getString("password"));
				bean.setNome(rs.getString("nome"));
				bean.setCognome(rs.getString("cognome"));
				bean.setDataRegistrazione(rs.getTimestamp("data_registrazione"));
				bean.setEmail(rs.getString("email"));
				bean.setTelefono(rs.getString("telefono"));

				utenti.add(bean);
			}
		}

		return utenti;
	}

	public synchronized List<Utente> retrieveAll() throws SQLException {
		return retrieveAll("");
	}

	@Override
	public synchronized Utente retrieveByKey(int id) throws SQLException {
		Utente bean = new Utente();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setInt(1, id);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					bean.setId(rs.getInt("id"));
					bean.setNomeUtente(rs.getString("nome_utente"));
					bean.setPassword(rs.getString("password"));
					bean.setNome(rs.getString("nome"));
					bean.setCognome(rs.getString("cognome"));
					bean.setDataRegistrazione(rs.getTimestamp("data_registrazione"));
					bean.setEmail(rs.getString("email"));
					bean.setTelefono(rs.getString("telefono"));
				}
			}
		}

		return bean;
	}

}
