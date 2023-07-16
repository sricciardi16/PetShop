package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.dto.Amministratore;
import it.petshop.utility.PetShopException;

public class AmministratoreDAO implements DAO<Amministratore> {

	private DataSource dataSource;
	private static final String TABLE_NAME = "amministratore";

	public AmministratoreDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public synchronized void create(Amministratore amministratore) throws PetShopException {
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (id, nome_utente, password) VALUES (?, ?, ?)";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setInt(1, amministratore.getId());
			preparedStatement.setString(2, amministratore.getNomeUtente());
			preparedStatement.setString(3, amministratore.getPassword());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore durante la creazione dell'amministratore", 500, e);
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
			throw new PetShopException("Errore durante l'eliminazione dell'amministratore", 500, e);
		}

		return result != 0;
	}

	@Override
	public synchronized List<Amministratore> retrieveAll(String order) throws PetShopException {
		List<Amministratore> amministratori = new ArrayList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL); ResultSet rs = preparedStatement.executeQuery()) {

			while (rs.next()) {
				Amministratore bean = new Amministratore();
				bean.setId(rs.getInt("id"));
				bean.setNomeUtente(rs.getString("nome_utente"));
				bean.setPassword(rs.getString("password"));

				amministratori.add(bean);
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero degli amministratori", 500, e);
		}

		return amministratori;
	}

	public synchronized List<Amministratore> retrieveAll() throws PetShopException {
		return retrieveAll("");
	}

	@Override
	public synchronized Amministratore retrieveByKey(int id) throws PetShopException {
		Amministratore bean = new Amministratore();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setInt(1, id);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					bean.setId(rs.getInt("id"));
					bean.setNomeUtente(rs.getString("nome_utente"));
					bean.setPassword(rs.getString("password"));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero dell'amministratore per chiave", 500, e);
		}

		return bean;
	}

	public boolean exists(String username, String password) throws PetShopException {
		String query = "SELECT * FROM amministratore WHERE nome_utente = ? AND password = ?";
		try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (Exception e) {
			throw new PetShopException("Errore durante il controllo dell'esistenza dell'amministratore", 500, e);
		}
	}

	public synchronized Amministratore retrieveByNomeUtente(String nomeUtente) throws PetShopException {
		Amministratore bean = null;
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE nome_utente = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setString(1, nomeUtente);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				if (rs.next()) {
					bean = new Amministratore();
					bean.setId(rs.getInt("id"));
					bean.setNomeUtente(rs.getString("nome_utente"));
					bean.setPassword(rs.getString("password"));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero dell'amministratore per nome utente", 500, e);
		}

		return bean;
	}

}
