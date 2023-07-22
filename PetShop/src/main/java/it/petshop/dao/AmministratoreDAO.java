package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import it.petshop.dto.Amministratore;
import it.petshop.utility.PetShopException;

public class AmministratoreDAO {

	private DataSource dataSource;
	private static final String TABLE_NAME = "amministratore";
	private static final String COLUMN_NAME_ID = "id";
	private static final String COLUMN_NAME_NOME_UTENTE = "nome_utente";
	private static final String COLUMN_NAME_PASSWORD = "password";

	public AmministratoreDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public synchronized void save(Amministratore amministratore) throws PetShopException {
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME_ID + ", " + COLUMN_NAME_NOME_UTENTE + ", " + COLUMN_NAME_PASSWORD + ") VALUES (?, ?, ?)";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setInt(1, amministratore.getId());
			preparedStatement.setString(2, amministratore.getNomeUtente());
			preparedStatement.setString(3, amministratore.getPassword());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore durante la creazione dell'amministratore", 500, e);
		}
	}

	public synchronized boolean deleteById(int id) throws PetShopException {
		int result = 0;
		String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_ID + " = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore durante l'eliminazione dell'amministratore", 500, e);
		}

		return result != 0;
	}

	public synchronized Amministratore findByNomeUtente(String nomeUtente) throws PetShopException {
		Amministratore bean = null;
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_NOME_UTENTE + " = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setString(1, nomeUtente);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				if (rs.next()) {
					bean = new Amministratore();
					bean.setId(rs.getInt(COLUMN_NAME_ID));
					bean.setNomeUtente(rs.getString(COLUMN_NAME_NOME_UTENTE));
					bean.setPassword(rs.getString(COLUMN_NAME_PASSWORD));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero dell'amministratore per nome utente", 500, e);
		}

		return bean;
	}

	public synchronized Amministratore findByNomeUtenteAndPassword(String nomeUtente, String password) throws PetShopException {
		Amministratore amministratore = null;
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_NOME_UTENTE + " = ? AND " + COLUMN_NAME_PASSWORD + " = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
			preparedStatement.setString(1, nomeUtente);
			preparedStatement.setString(2, password);

			try (ResultSet rs = preparedStatement.executeQuery()) {
				if (rs.next()) {
					amministratore = new Amministratore();
					amministratore.setId(rs.getInt(COLUMN_NAME_ID));
					amministratore.setNomeUtente(rs.getString(COLUMN_NAME_NOME_UTENTE));
					amministratore.setPassword(rs.getString(COLUMN_NAME_PASSWORD));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero dell'amministratore per nome utente e password", 500, e);
		}

		return amministratore;
	}
}
