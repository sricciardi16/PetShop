package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.dto.Ordine;
import it.petshop.dto.Utente;
import it.petshop.utility.DatabaseUtil;
import it.petshop.utility.PetShopException;

public class OrdineDAO {

	private DataSource dataSource;
	private static final String TABLE_NAME = "ordine";

	public OrdineDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public synchronized int save(Ordine ordine) throws PetShopException {
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (prezzo, id_utente, id_metodo_pagamento, id_metodo_spedizione, id_indirizzo) VALUES (?, ?, ?, ?, ?)";
		int generatedId = -1;

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setDouble(1, ordine.getPrezzo());
			preparedStatement.setInt(2, ordine.getIdUtente());
			preparedStatement.setInt(3, ordine.getIdMetodoPagamento());
			preparedStatement.setInt(4, ordine.getIdMetodoSpedizione());
			preparedStatement.setInt(5, ordine.getIdIndirizzo());

			generatedId = DatabaseUtil.getAutoIncrementValue(preparedStatement);

		} catch (SQLException e) {
			throw new PetShopException("Errore durante la creazione dell'ordine", 500, e);
		}

		return generatedId;
	}

	public synchronized boolean deleteById(int id) throws PetShopException {
		int result = 0;
		String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore durante l'eliminazione dell'ordine", 500, e);
		}

		return result != 0;
	}

	public synchronized List<Ordine> findAll(String order) throws PetShopException {
		List<Ordine> ordini = new ArrayList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL); ResultSet rs = preparedStatement.executeQuery()) {

			while (rs.next()) {
				Ordine bean = new Ordine();
				bean.setId(rs.getInt("id"));
				bean.setDataOra(rs.getTimestamp("data_ora"));
				bean.setPrezzo(rs.getDouble("prezzo"));
				bean.setStato(rs.getString("stato"));
				bean.setIdUtente(rs.getInt("id_utente"));
				bean.setIdMetodoPagamento(rs.getInt("id_metodo_pagamento"));
				bean.setIdMetodoSpedizione(rs.getInt("id_metodo_spedizione"));
				bean.setIdIndirizzo(rs.getInt("id_indirizzo"));

				ordini.add(bean);
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero degli ordini", 500, e);
		}

		return ordini;
	}

	public synchronized List<Ordine> findAll() throws PetShopException {
		return findAll("");
	}

	public synchronized Ordine findById(int id) throws PetShopException {
		Ordine bean = new Ordine();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setInt(1, id);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					bean.setId(rs.getInt("id"));
					bean.setDataOra(rs.getTimestamp("data_ora"));
					bean.setPrezzo(rs.getDouble("prezzo"));
					bean.setStato(rs.getString("stato"));
					bean.setIdUtente(rs.getInt("id_utente"));
					bean.setIdMetodoPagamento(rs.getInt("id_metodo_pagamento"));
					bean.setIdMetodoSpedizione(rs.getInt("id_metodo_spedizione"));
					bean.setIdIndirizzo(rs.getInt("id_indirizzo"));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero dell'ordine", 500, e);
		}

		return bean;
	}

	public List<Ordine> findAllByUtente(Utente utente) throws PetShopException {
		List<Ordine> ordini = new ArrayList<>();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_utente = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setInt(1, utente.getId());
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					Ordine bean = new Ordine();
					bean.setId(rs.getInt("id"));
					bean.setDataOra(rs.getTimestamp("data_ora"));
					bean.setPrezzo(rs.getDouble("prezzo"));
					bean.setStato(rs.getString("stato"));
					bean.setIdUtente(rs.getInt("id_utente"));
					bean.setIdMetodoPagamento(rs.getInt("id_metodo_pagamento"));
					bean.setIdMetodoSpedizione(rs.getInt("id_metodo_spedizione"));
					bean.setIdIndirizzo(rs.getInt("id_indirizzo"));

					ordini.add(bean);
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero degli ordini dell'utente", 500, e);
		}

		return ordini;
	}
	
	public List<Ordine> findAllByUtenteDataSorted(Utente utente, String startDate, String endDate, String ordinamento, String ordine) throws PetShopException {
	    List<Ordine> ordini = new ArrayList<>();
	    String baseSQL = "SELECT * FROM " + TABLE_NAME + " WHERE 1=1";
	    String userCondition = (utente != null) ? " AND id_utente = ? " : "";
	    String startDateCondition = (startDate != null && !startDate.isBlank()) ? " AND data_ora >= ? " : "";
	    String endDateCondition = (endDate != null && !endDate.isBlank()) ? " AND data_ora <= ? " : "";
	    String orderCondition = (ordinamento != null && ordine != null) ? " ORDER BY " + ordinamento + " " + ordine : "";

	    String selectSQL = baseSQL + userCondition + startDateCondition + endDateCondition + orderCondition;

	    try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

	        int paramIndex = 1;
	        if (utente != null) {
	            preparedStatement.setInt(paramIndex++, utente.getId());
	        }
	        if (startDate != null && !startDate.isBlank()) {
	            preparedStatement.setString(paramIndex++, startDate);
	        }
	        if (endDate != null && !endDate.isBlank()) {
	            preparedStatement.setString(paramIndex++, endDate);
	        }

	        try (ResultSet rs = preparedStatement.executeQuery()) {
	            while (rs.next()) {
	                Ordine bean = new Ordine();
	                bean.setId(rs.getInt("id"));
	                bean.setDataOra(rs.getTimestamp("data_ora"));
	                bean.setPrezzo(rs.getDouble("prezzo"));
	                bean.setStato(rs.getString("stato"));
	                bean.setIdUtente(rs.getInt("id_utente"));
	                bean.setIdMetodoPagamento(rs.getInt("id_metodo_pagamento"));
	                bean.setIdMetodoSpedizione(rs.getInt("id_metodo_spedizione"));
	                bean.setIdIndirizzo(rs.getInt("id_indirizzo"));

	                ordini.add(bean);
	            }
	        }
	    } catch (SQLException e) {
	        throw new PetShopException("Errore durante il recupero degli ordini" + e.getMessage(), 500, e);
	    }

	    return ordini;
	}
	
	public synchronized boolean updateStatoById(int id, String nuovoStato) throws PetShopException {
	    String updateSQL = "UPDATE " + TABLE_NAME + " SET stato = ? WHERE id = ?";
	    int affectedRows = 0;

	    try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
	        preparedStatement.setString(1, nuovoStato);
	        preparedStatement.setInt(2, id);
	        affectedRows = preparedStatement.executeUpdate();
	    } catch (SQLException e) {
	        throw new PetShopException("Errore durante l'aggiornamento dello stato dell'ordine", 500, e);
	    }
	    return affectedRows > 0;
	}




}
