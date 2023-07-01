package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.model.Categoria;
import it.petshop.model.Prodotto;

public class ProdottoDAO implements DAO<Prodotto> {

	private DataSource dataSource;
	private static final String TABLE_NAME = "prodotto";

	public ProdottoDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public synchronized void create(Prodotto prodotto) throws SQLException {
		String insertSQL = "INSERT INTO " + TABLE_NAME
				+ " (id, nome, descrizione, prezzo, immagine, in_magazzino, id_categoria) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setInt(1, prodotto.getId());
			preparedStatement.setString(2, prodotto.getNome());
			preparedStatement.setString(3, prodotto.getDescrizione());
			preparedStatement.setDouble(4, prodotto.getPrezzo());
			preparedStatement.setString(5, prodotto.getImmagine());
			preparedStatement.setInt(6, prodotto.getInMagazzino());
			preparedStatement.setInt(7, prodotto.getIdCategoria());

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
	public synchronized List<Prodotto> retrieveAll(String order) throws SQLException {
		List<Prodotto> prodotti = new ArrayList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
				ResultSet rs = preparedStatement.executeQuery()) {

			while (rs.next()) {
				Prodotto bean = new Prodotto();
				bean.setId(rs.getInt("id"));
				bean.setNome(rs.getString("nome"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setPrezzo(rs.getDouble("prezzo"));
				bean.setImmagine(rs.getString("immagine"));
				bean.setInMagazzino(rs.getInt("in_magazzino"));
				bean.setIdCategoria(rs.getInt("id_categoria"));

				prodotti.add(bean);
			}
		}

		return prodotti;
	}

	public synchronized List<Prodotto> retrieveAll() throws SQLException {
		return retrieveAll("");
	}

	@Override
	public synchronized Prodotto retrieveByKey(int id) throws SQLException {
		Prodotto bean = new Prodotto();
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
					bean.setInMagazzino(rs.getInt("in_magazzino"));
					bean.setIdCategoria(rs.getInt("id_categoria"));
				}
			}
		}

		return bean;
	}

	public synchronized List<Prodotto> retrieve(Categoria categoria, int offset, int limit, String order, boolean asc)
			throws SQLException {
		List<Prodotto> prodotti = new ArrayList<>();

		String selectSQL = "SELECT * FROM prodotto, categoria WHERE prodotto.id_categoria = categoria.id AND animale = ?";

		if (!categoria.getTipologia().isBlank())
			selectSQL += " AND tipologia = ?";

		if (!categoria.getTipologiaIn().isBlank())
			selectSQL += " AND tipologia_in = ?";

		if (!order.isBlank())
			selectSQL += " ORDER BY " + order;

		if (!asc)
			selectSQL += " DESC";

		selectSQL += " LIMIT ? OFFSET ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			int index = 1;
			preparedStatement.setString(index++, categoria.getAnimale());

			if (!categoria.getTipologia().isBlank())
				preparedStatement.setString(index++, categoria.getTipologia());

			if (!categoria.getTipologiaIn().isBlank())
				preparedStatement.setString(index++, categoria.getTipologiaIn());

			preparedStatement.setInt(index++, limit);
			preparedStatement.setInt(index++, offset);

			try (ResultSet rs = preparedStatement.executeQuery()) {
				
				while (rs.next()) {
					Prodotto bean = new Prodotto();
					bean.setId(rs.getInt("id"));
					bean.setNome(rs.getString("nome"));
					bean.setDescrizione(rs.getString("descrizione"));
					bean.setPrezzo(rs.getDouble("prezzo"));
					bean.setImmagine(rs.getString("immagine"));
					bean.setInMagazzino(rs.getInt("in_magazzino"));
					bean.setIdCategoria(rs.getInt("id_categoria"));

					prodotti.add(bean);
					
				}
			}
		}

		return prodotti;
	}

}
