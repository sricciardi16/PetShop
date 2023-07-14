package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.utility.PetShopException;
import it.petshop.model.MetodoPagamento;

public class MetodoPagamentoDAO implements DAO<MetodoPagamento> {

	private DataSource dataSource;
	private static final String TABLE_NAME = "metodo_pagamento";

	public MetodoPagamentoDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public synchronized void create(MetodoPagamento metodoPagamento) throws PetShopException {
		String insertSQL = "INSERT INTO " + TABLE_NAME
				+ " (tipo) VALUES (?)";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setInt(1, metodoPagamento.getId());
			preparedStatement.setString(2, metodoPagamento.getTipo());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore Server", e);
		}
	}
	
	public synchronized int createAndReturnId(MetodoPagamento metodoPagamento) throws PetShopException {
	    String insertSQL = "INSERT INTO " + TABLE_NAME + " (tipo) VALUES (?)";
	    int generatedId = -1;

	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
	        preparedStatement.setString(1, metodoPagamento.getTipo());
	        preparedStatement.executeUpdate();

	        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            generatedId = generatedKeys.getInt(1);
	        }
	    } catch (SQLException e) {
	        throw new PetShopException("Errore Server", e);
	    }

	    return generatedId;
	}

	@Override
	public synchronized boolean delete(int id) throws PetShopException {
		int result = 0;
		String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore Server", e);
		}

		return result != 0;
	}

	@Override
	public synchronized List<MetodoPagamento> retrieveAll(String order) throws PetShopException {
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

				metodiPagamento.add(bean);
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore Server", e);
		}

		return metodiPagamento;
	}

	public synchronized List<MetodoPagamento> retrieveAll() throws PetShopException {
		return retrieveAll("");
	}

	@Override
	public synchronized MetodoPagamento retrieveByKey(int id) throws PetShopException {
		MetodoPagamento bean = new MetodoPagamento();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setInt(1, id);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					bean.setId(rs.getInt("id"));
					bean.setTipo(rs.getString("tipo"));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore Server", e);
		}

		return bean;
	}
	
	public synchronized MetodoPagamento retrieveByTipo(String tipo) throws PetShopException {
		MetodoPagamento bean = new MetodoPagamento();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE tipo = ? LIMIT 1";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setString(1, tipo);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				if (rs.next()) {
					bean.setId(rs.getInt("id"));
					bean.setTipo(rs.getString("tipo"));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore Server", e);
		}

		return bean;
	}


}
