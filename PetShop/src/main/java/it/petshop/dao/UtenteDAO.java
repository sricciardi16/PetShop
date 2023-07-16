package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.dto.Utente;
import it.petshop.utility.PetShopException;

public class UtenteDAO implements DAO<Utente> {

	private DataSource dataSource;
	private static final String TABLE_NAME = "utente";

	public UtenteDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public synchronized void create(Utente utente) throws PetShopException {
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (id, nome_utente, password, nome, cognome, email, telefono) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setInt(1, utente.getId());
			preparedStatement.setString(2, utente.getNomeUtente());
			preparedStatement.setString(3, utente.getPassword());
			preparedStatement.setString(4, utente.getNome());
			preparedStatement.setString(5, utente.getCognome());
			preparedStatement.setString(6, utente.getEmail());
			preparedStatement.setString(7, utente.getTelefono());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore durante la creazione dell'utente", 500, e);
		}
	}

	@Override
	public synchronized boolean delete(int id) throws PetShopException {
		int result = 0;
		String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore durante l'eliminazione dell'utente", 500, e);
		}

		return result != 0;
	}

	@Override
	public synchronized List<Utente> retrieveAll(String order) throws PetShopException {
		List<Utente> utenti = new ArrayList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL); ResultSet rs = preparedStatement.executeQuery()) {

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
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero degli utenti", 500, e);
		}

		return utenti;
	}

	public synchronized List<Utente> retrieveAll() throws PetShopException {
		return retrieveAll("");
	}

	@Override
	public synchronized Utente retrieveByKey(int id) throws PetShopException {
		Utente bean = new Utente();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

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
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero dell'utente", 500, e);
		}

		return bean;
	}

	public boolean exists(String username, String password) throws PetShopException {
		String query = "SELECT * FROM utente WHERE nome_utente = ? AND password = ?";
		try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			throw new PetShopException("Errore durante la verifica dell'esistenza dell'utente", 500, e);
		}
	}

	public synchronized Utente retrieveByNomeUtente(String nomeUtente) throws PetShopException {
		Utente bean = new Utente();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE nome_utente = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setString(1, nomeUtente);
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
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero dell'utente", 500, e);
		}

		return bean;
	}
}
