package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.model.MetodoPagamento;

public class MetodoPagamentoDAO implements DAO<MetodoPagamento> {

	private DataSource dataSource;
	private static final String TABLE_NAME = "metodo_pagamento";

	public MetodoPagamentoDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public synchronized void create(MetodoPagamento metodoPagamento) throws SQLException {
		String insertSQL = "INSERT INTO " + TABLE_NAME
				+ " (id, tipo, token, dettagli_visualizzazione, id_utente) VALUES (?, ?, ?, ?, ?)";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setInt(1, metodoPagamento.getId());
			preparedStatement.setString(2, metodoPagamento.getTipo());
			preparedStatement.setString(3, metodoPagamento.getToken());
			preparedStatement.setString(4, metodoPagamento.getDettagliVisualizzazione());
			preparedStatement.setInt(5, metodoPagamento.getIdUtente());

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
	public synchronized List<MetodoPagamento> retrieveAll(String order) throws SQLException {
		List<MetodoPagamento> metodiPagamento = new ArrayList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
				ResultSet rs = preparedStatement.executeQuery()) {

			while (rs.next()) {
				MetodoPagamento bean = new MetodoPagamento();
				bean.setId(rs.getInt("id"));
				bean.setTipo(rs.getString("tipo"));
				bean.setToken(rs.getString("token"));
				bean.setDettagliVisualizzazione(rs.getString("dettagli_visualizzazione"));
				bean.setIdUtente(rs.getInt("id_utente"));

				metodiPagamento.add(bean);
			}
		}

		return metodiPagamento;
	}

	public synchronized List<MetodoPagamento> retrieveAll() throws SQLException {
		return retrieveAll("");
	}

	@Override
	public synchronized MetodoPagamento retrieveByKey(int id) throws SQLException {
		MetodoPagamento bean = new MetodoPagamento();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setInt(1, id);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					bean.setId(rs.getInt("id"));
					bean.setTipo(rs.getString("tipo"));
					bean.setToken(rs.getString("token"));
					bean.setDettagliVisualizzazione(rs.getString("dettagli_visualizzazione"));
					bean.setIdUtente(rs.getInt("id_utente"));
				}
			}
		}

		return bean;
	}

}
