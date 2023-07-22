package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import it.petshop.dto.MetodoPagamento;
import it.petshop.utility.DatabaseUtil;
import it.petshop.utility.PetShopException;

public class MetodoPagamentoDAO {

	private DataSource dataSource;
	private static final String TABLE_NAME = "metodo_pagamento";
	private static final String COLUMN_NAME_ID = "id";
	private static final String COLUMN_NAME_TIPO = "tipo";

	public MetodoPagamentoDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public synchronized int save(MetodoPagamento metodoPagamento) throws PetShopException {
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME_TIPO + ") VALUES (?)";
		int generatedId = -1;

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, metodoPagamento.getTipo());

			generatedId = DatabaseUtil.getAutoIncrementValue(preparedStatement);

		} catch (SQLException e) {
			throw new PetShopException("Errore durante la creazione del metodo di pagamento", 500, e);
		}

		return generatedId;
	}

	public synchronized MetodoPagamento findById(int id) throws PetShopException {
		MetodoPagamento bean = new MetodoPagamento();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_ID + " = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setInt(1, id);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					bean.setId(rs.getInt(COLUMN_NAME_ID));
					bean.setTipo(rs.getString(COLUMN_NAME_TIPO));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero del metodo di pagamento", 500, e);
		}

		return bean;
	}

	public synchronized MetodoPagamento findFirstByTipo(String tipo) throws PetShopException {
		MetodoPagamento bean = new MetodoPagamento();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_TIPO + " = ? LIMIT 1";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setString(1, tipo);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				if (rs.next()) {
					bean.setId(rs.getInt(COLUMN_NAME_ID));
					bean.setTipo(rs.getString(COLUMN_NAME_TIPO));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero del metodo di pagamento", 500, e);
		}

		return bean;
	}
}
