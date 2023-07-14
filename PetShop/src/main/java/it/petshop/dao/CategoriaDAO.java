package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.utility.PetShopException;
import it.petshop.model.Categoria;

public class CategoriaDAO implements DAO<Categoria> {

	private DataSource dataSource;
	private static final String TABLE_NAME = "categoria";

	public CategoriaDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public synchronized void create(Categoria categoria) throws PetShopException {
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (id, animale, tipologia, tipologia_in) VALUES (?, ?, ?, ?)";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setInt(1, categoria.getId());
			preparedStatement.setString(2, categoria.getAnimale());
			preparedStatement.setString(3, categoria.getTipologia());
			preparedStatement.setString(4, categoria.getTipologiaIn());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore Server", e);
		}
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
	public synchronized List<Categoria> retrieveAll(String order) throws PetShopException {
		List<Categoria> categorie = new ArrayList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
				ResultSet rs = preparedStatement.executeQuery()) {

			while (rs.next()) {
				Categoria bean = new Categoria();
				bean.setId(rs.getInt("id"));
				bean.setAnimale(rs.getString("animale"));
				bean.setTipologia(rs.getString("tipologia"));
				bean.setTipologiaIn(rs.getString("tipologia_in"));

				categorie.add(bean);
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore Server", e);
		}

		return categorie;
	}

	public synchronized List<Categoria> retrieveAll() throws PetShopException {
		return retrieveAll("");
	}

	@Override
	public synchronized Categoria retrieveByKey(int id) throws PetShopException {
		Categoria bean = new Categoria();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setInt(1, id);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					bean.setId(rs.getInt("id"));
					bean.setAnimale(rs.getString("animale"));
					bean.setTipologia(rs.getString("tipologia"));
					bean.setTipologiaIn(rs.getString("tipologia_in"));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore Server", e);
		}

		return bean;
	}

	public synchronized List<Categoria> retrieve(Categoria categoria) throws PetShopException {
		List<Categoria> categorie = new ArrayList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE animale = ?";

		if (!categoria.getTipologia().isBlank())
			selectSQL += " AND tipologia = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setString(1, categoria.getAnimale());

			if (!categoria.getTipologia().isBlank())
				preparedStatement.setString(2, categoria.getTipologia());

			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					Categoria bean = new Categoria();
					bean.setId(rs.getInt("id"));
					bean.setAnimale(rs.getString("animale"));
					bean.setTipologia(rs.getString("tipologia"));
					bean.setTipologiaIn(rs.getString("tipologia_in"));

					categorie.add(bean);
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore Server", e);
		}

		return categorie;
	}

}
