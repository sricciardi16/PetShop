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

import static it.petshop.utility.DatabaseUtil.*;

public class UtenteDAO {

	private DataSource dataSource;
	private static final String TABLE_NAME = "utente";
	private static final String COLUMN_NAME_ID = "id";
	private static final String COLUMN_NAME_NOME_UTENTE = "nome_utente";
	private static final String COLUMN_NAME_PASSWORD = "password";
	private static final String COLUMN_NAME_NOME = "nome";
	private static final String COLUMN_NAME_COGNOME = "cognome";
	private static final String COLUMN_NAME_EMAIL = "email";
	private static final String COLUMN_NAME_TELEFONO = "telefono";
	private static final String COLUMN_NAME_DATA_REGISTRAZIONE = "data_registrazione";

	public UtenteDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public synchronized void save(Utente utente) throws PetShopException {
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME_ID + ", " + COLUMN_NAME_NOME_UTENTE + ", " + COLUMN_NAME_PASSWORD + ", " + COLUMN_NAME_NOME + ", " + COLUMN_NAME_COGNOME + ", " + COLUMN_NAME_EMAIL + ", " + COLUMN_NAME_TELEFONO + ") VALUES (?, ?, ?, ?, ?, ?, ?)";

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
		String deleteSQL = "DELETE FROM " + TABLE_NAME + WHERE + COLUMN_NAME_ID + " = ?";

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
		String selectSQL = SELECT_ALL_FROM + TABLE_NAME + WHERE + COLUMN_NAME_NOME_UTENTE + " = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
			preparedStatement.setString(1, nomeUtente);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				if (rs.next()) {
					utente = new Utente();
					utente.setId(rs.getInt(COLUMN_NAME_ID));
					utente.setNomeUtente(rs.getString(COLUMN_NAME_NOME_UTENTE));
					utente.setPassword(rs.getString(COLUMN_NAME_PASSWORD));
					utente.setNome(rs.getString(COLUMN_NAME_NOME));
					utente.setCognome(rs.getString(COLUMN_NAME_COGNOME));
					utente.setEmail(rs.getString(COLUMN_NAME_EMAIL));
					utente.setTelefono(rs.getString(COLUMN_NAME_TELEFONO));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero dell'utente per nome utente", 500, e);
		}

		return utente;
	}

	public synchronized Utente findByNomeUtenteAndPassword(String nomeUtente, String password) throws PetShopException {
		Utente utente = null;
		String selectSQL = SELECT_ALL_FROM + TABLE_NAME + WHERE + COLUMN_NAME_NOME_UTENTE + " = ? AND " + COLUMN_NAME_PASSWORD + " = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
			preparedStatement.setString(1, nomeUtente);
			preparedStatement.setString(2, password);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				if (rs.next()) {
					utente = new Utente();
					utente.setId(rs.getInt(COLUMN_NAME_ID));
					utente.setNomeUtente(rs.getString(COLUMN_NAME_NOME_UTENTE));
					utente.setPassword(rs.getString(COLUMN_NAME_PASSWORD));
					utente.setNome(rs.getString(COLUMN_NAME_NOME));
					utente.setCognome(rs.getString(COLUMN_NAME_COGNOME));
					utente.setEmail(rs.getString(COLUMN_NAME_EMAIL));
					utente.setDataRegistrazione(rs.getTimestamp(COLUMN_NAME_DATA_REGISTRAZIONE));
					utente.setTelefono(rs.getString(COLUMN_NAME_TELEFONO));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero dell'utente per nome utente e password", 500, e);
		}

		return utente;
	}
	
	public synchronized List<Utente> findAll() throws PetShopException {
		List<Utente> utenti = new ArrayList<>();
		String selectSQL = SELECT_ALL_FROM + TABLE_NAME;

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					Utente utente = new Utente();
					utente.setId(rs.getInt(COLUMN_NAME_ID));
					utente.setNomeUtente(rs.getString(COLUMN_NAME_NOME_UTENTE));
					utente.setPassword(rs.getString(COLUMN_NAME_PASSWORD));
					utente.setNome(rs.getString(COLUMN_NAME_NOME));
					utente.setCognome(rs.getString(COLUMN_NAME_COGNOME));
					utente.setEmail(rs.getString(COLUMN_NAME_EMAIL));
					utente.setTelefono(rs.getString(COLUMN_NAME_TELEFONO));
					utenti.add(utente);
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero degli utenti", 500, e);
		}

		return utenti;
	}
	
	public synchronized Utente findById(int id) throws PetShopException {
		Utente utente = null;
		String selectSQL = SELECT_ALL_FROM + TABLE_NAME + WHERE + COLUMN_NAME_ID + " = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
			preparedStatement.setInt(1, id);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				if (rs.next()) {
					utente = new Utente();
					utente.setId(rs.getInt(COLUMN_NAME_ID));
					utente.setNomeUtente(rs.getString(COLUMN_NAME_NOME_UTENTE));
					utente.setPassword(rs.getString(COLUMN_NAME_PASSWORD));
					utente.setNome(rs.getString(COLUMN_NAME_NOME));
					utente.setCognome(rs.getString(COLUMN_NAME_COGNOME));
					utente.setEmail(rs.getString(COLUMN_NAME_EMAIL));
					utente.setTelefono(rs.getString(COLUMN_NAME_TELEFONO));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero dell'utente per ID", 500, e);
		}

		return utente;
	}
}
