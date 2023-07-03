package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.model.Amministratore;

public class AmministratoreDAO implements DAO<Amministratore> {

	private DataSource dataSource;
	private static final String TABLE_NAME = "amministratore";

	public AmministratoreDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public synchronized void create(Amministratore amministratore) throws SQLException {
		String insertSQL = "INSERT INTO " + TABLE_NAME
				+ " (id, nome_utente, password) VALUES (?, ?, ?)";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setInt(1, amministratore.getId());
			preparedStatement.setString(2, amministratore.getNomeUtente());
			preparedStatement.setString(3, amministratore.getPassword());

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
	public synchronized List<Amministratore> retrieveAll(String order) throws SQLException {
	    List<Amministratore> amministratori = new ArrayList<>();

	    String selectSQL = "SELECT * FROM " + TABLE_NAME;

	    if (order != null && !order.equals("")) {
	        selectSQL += " ORDER BY " + order;
	    }

	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
	         ResultSet rs = preparedStatement.executeQuery()) {

	        while (rs.next()) {
	            Amministratore bean = new Amministratore();
	            bean.setId(rs.getInt("id"));
	            bean.setNomeUtente(rs.getString("nome_utente"));
	            bean.setPassword(rs.getString("password"));

	            amministratori.add(bean);
	        }
	    }

	    return amministratori;
	}
	
	public synchronized List<Amministratore> retrieveAll() throws SQLException {
	    return retrieveAll("");
	}


	@Override
	public synchronized Amministratore retrieveByKey(int id) throws SQLException {
	    Amministratore bean = new Amministratore();
	    String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

	        preparedStatement.setInt(1, id);
	        try (ResultSet rs = preparedStatement.executeQuery()) {
	            while (rs.next()) {
	                bean.setId(rs.getInt("id"));
	                bean.setNomeUtente(rs.getString("nome_utente"));
	                bean.setPassword(rs.getString("password"));
	            }
	        }
	    }

	    return bean;
	}
	
	public boolean exits(String username, String password) throws SQLException {
        String query = "SELECT * FROM amministratore WHERE nome_utente = ? AND password = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

}
