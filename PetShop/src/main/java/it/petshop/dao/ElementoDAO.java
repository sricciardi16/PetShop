package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.model.Elemento;

public class ElementoDAO implements DAO<Elemento> {

	private DataSource dataSource;
	private static final String TABLE_NAME = "elemento";

	public ElementoDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public synchronized void create(Elemento elemento) throws SQLException {
		String insertSQL = "INSERT INTO " + TABLE_NAME
				+ " (id, nome, descrizione, prezzo, immagine, quantita, id_prodotto, id_ordine) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setInt(1, elemento.getId());
			preparedStatement.setString(2, elemento.getNome());
			preparedStatement.setString(3, elemento.getDescrizione());
			preparedStatement.setDouble(4, elemento.getPrezzo());
			preparedStatement.setString(5, elemento.getImmagine());
			preparedStatement.setInt(6, elemento.getQuantita());
			preparedStatement.setInt(7, elemento.getIdProdotto());
			preparedStatement.setInt(8, elemento.getIdOrdine());

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
	public synchronized List<Elemento> retrieveAll(String order) throws SQLException {
		List<Elemento> elementi = new ArrayList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
				ResultSet rs = preparedStatement.executeQuery()) {

			while (rs.next()) {
				Elemento bean = new Elemento();
				bean.setId(rs.getInt("id"));
				bean.setNome(rs.getString("nome"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setPrezzo(rs.getDouble("prezzo"));
				bean.setImmagine(rs.getString("immagine"));
				bean.setQuantita(rs.getInt("quantita"));
				bean.setIdProdotto(rs.getInt("id_prodotto"));
				bean.setIdOrdine(rs.getInt("id_ordine"));

				elementi.add(bean);
			}
		}

		return elementi;
	}

	public synchronized List<Elemento> retrieveAll() throws SQLException {
		return retrieveAll("");
	}

	@Override
	public synchronized Elemento retrieveByKey(int id) throws SQLException {
		Elemento bean = new Elemento();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setInt(1, id);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					bean.setId(rs.getInt("id"));
					bean.setNome(rs.getString("nome"));
					bean.setDescrizione(rs.getString("descrizione"));
					bean.setPrezzo(rs.getDouble("prezzo"));
					bean.setImmagine(rs.getString("immagine"));
					bean.setQuantita(rs.getInt("quantita"));
					bean.setIdProdotto(rs.getInt("id_prodotto"));
					bean.setIdOrdine(rs.getInt("id_ordine"));
				}
			}
		}

		return bean;
	}

}
