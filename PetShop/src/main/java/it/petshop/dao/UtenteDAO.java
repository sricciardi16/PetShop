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

public class UtenteDAO {

	private DataSource dataSource;
	private static final String TABLE_NAME = "utente";

	public UtenteDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public synchronized void save(Utente utente) throws PetShopException {
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

	public synchronized boolean deleteById(int id) throws PetShopException {
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

	public synchronized Utente findByNomeUtente(String nomeUtente) throws PetShopException {
		Utente utente = null;
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE nome_utente = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
			preparedStatement.setString(1, nomeUtente);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				if (rs.next()) {
					utente = new Utente();
					utente.setId(rs.getInt("id"));
					utente.setNomeUtente(rs.getString("nome_utente"));
					utente.setPassword(rs.getString("password"));
					utente.setNome(rs.getString("nome"));
					utente.setCognome(rs.getString("cognome"));
					utente.setEmail(rs.getString("email"));
					utente.setTelefono(rs.getString("telefono"));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero dell'utente per nome utente", 500, e);
		}

		return utente;
	}

	public synchronized Utente findByNomeUtenteAndPassword(String nomeUtente, String password) throws PetShopException {
		Utente utente = null;
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE nome_utente = ? AND password = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
			preparedStatement.setString(1, nomeUtente);
			preparedStatement.setString(2, password);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				if (rs.next()) {
					utente = new Utente();
					utente.setId(rs.getInt("id"));
					utente.setNomeUtente(rs.getString("nome_utente"));
					utente.setPassword(rs.getString("password"));
					utente.setNome(rs.getString("nome"));
					utente.setCognome(rs.getString("cognome"));
					utente.setEmail(rs.getString("email"));
					utente.setTelefono(rs.getString("telefono"));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero dell'utente per nome utente e password", 500, e);
		}

		return utente;
	}

}
