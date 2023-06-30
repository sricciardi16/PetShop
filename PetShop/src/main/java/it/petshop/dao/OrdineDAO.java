package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.model.Ordine;

public class OrdineDAO implements DAO<Ordine> {

	private DataSource dataSource;
	private static final String TABLE_NAME = "ordine";

	public OrdineDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public synchronized void create(Ordine ordine) throws SQLException {
		String insertSQL = "INSERT INTO " + TABLE_NAME
				+ " (id, data_ora, prezzo, stato, id_utente, id_metodo_pagamento, id_metodo_spedizione, id_indirizzo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setInt(1, ordine.getId());
			preparedStatement.setTimestamp(2, ordine.getDataOra());
			preparedStatement.setDouble(3, ordine.getPrezzo());
			preparedStatement.setString(4, ordine.getStato());
			preparedStatement.setInt(5, ordine.getIdUtente());
			preparedStatement.setInt(6, ordine.getIdMetodoPagamento());
			preparedStatement.setInt(7, ordine.getIdMetodoSpedizione());
			preparedStatement.setInt(8, ordine.getIdIndirizzo());

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
	public synchronized List<Ordine> retrieveAll(String order) throws SQLException {
		List<Ordine> ordini = new ArrayList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
				ResultSet rs = preparedStatement.executeQuery()) {

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

		return ordini;
	}

	public synchronized List<Ordine> retrieveAll() throws SQLException {
		return retrieveAll("");
	}

	@Override
	public synchronized Ordine retrieveByKey(int id) throws SQLException {
		Ordine bean = new Ordine();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

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
		}

		return bean;
	}

}
