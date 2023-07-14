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
import it.petshop.model.Prodotto;

public class ProdottoDAO implements DAO<Prodotto> {

	private DataSource dataSource;
	private static final String TABLE_NAME = "prodotto";

	public ProdottoDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public synchronized void create(Prodotto prodotto) throws PetShopException {
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (id, nome, descrizione, prezzo, immagine, in_magazzino, id_categoria) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setInt(1, prodotto.getId());
			preparedStatement.setString(2, prodotto.getNome());
			preparedStatement.setString(3, prodotto.getDescrizione());
			preparedStatement.setDouble(4, prodotto.getPrezzo());
			preparedStatement.setString(5, prodotto.getImmagine());
			preparedStatement.setInt(6, prodotto.getInMagazzino());
			preparedStatement.setInt(7, prodotto.getIdCategoria());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore Server", e);
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
			throw new PetShopException("Errore Server", e);
		}

		return result != 0;
	}

	@Override
	public synchronized List<Prodotto> retrieveAll(String order) throws PetShopException {
		List<Prodotto> prodotti = new ArrayList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL); ResultSet rs = preparedStatement.executeQuery()) {

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
		} catch (SQLException e) {
			throw new PetShopException("Errore Server", e);
		}

		return prodotti;
	}

	public synchronized List<Prodotto> retrieveAll() throws PetShopException {
		return retrieveAll("");
	}

	@Override
	public synchronized Prodotto retrieveByKey(int id) throws PetShopException {
		Prodotto bean = new Prodotto();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

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
		} catch (SQLException e) {
			throw new PetShopException("Errore Server", e);
		}

		return bean;
	}

	public synchronized List<Prodotto> retrieve(Categoria categoria, int offset, int limit, String order, boolean asc) throws PetShopException {
		List<Prodotto> prodotti = new ArrayList<>();

		String selectSQL = "SELECT * FROM prodotto, categoria WHERE prodotto.id_categoria = categoria.id AND animale = ? AND prodotto.in_magazzino != 0 ";

		if (!categoria.getTipologia().isBlank())
			selectSQL += " AND tipologia = ?";

		if (!categoria.getTipologiaIn().isBlank())
			selectSQL += " AND tipologia_in = ?";

		if (!order.isBlank())
			selectSQL += " ORDER BY " + order;

		if (!asc)
			selectSQL += " DESC";

		selectSQL += " LIMIT ? OFFSET ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

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
		} catch (SQLException e) {
			throw new PetShopException("Errore Server", e);
		}

		return prodotti;
	}

	public synchronized int numeroPagine(Categoria categoria, int limit) throws PetShopException {
		int result = 0;

		String countSQL = "SELECT COUNT(*) AS count FROM prodotto, categoria WHERE prodotto.id_categoria = categoria.id AND animale = ?";
		if (!categoria.getTipologia().isBlank())
			countSQL += " AND tipologia = ?";

		if (!categoria.getTipologiaIn().isBlank())
			countSQL += " AND tipologia_in = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(countSQL)) {

			int index = 1;
			preparedStatement.setString(index++, categoria.getAnimale());

			if (!categoria.getTipologia().isBlank())
				preparedStatement.setString(index++, categoria.getTipologia());

			if (!categoria.getTipologiaIn().isBlank())
				preparedStatement.setString(index++, categoria.getTipologiaIn());

			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					int count = rs.getInt("count");
					result = count / limit;

					if (count % limit != 0) {
						result++;
					}
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore Server", e);
		}

		return result;
	}
	
	public synchronized void updateInMagazzino(int idProdotto, int quantita) throws PetShopException {
	    String updateSQL = "UPDATE " + TABLE_NAME + " SET in_magazzino = in_magazzino - ? WHERE id = ?";

	    try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
	        preparedStatement.setInt(1, quantita);
	        preparedStatement.setInt(2, idProdotto);
	        
	        int rowsUpdated = preparedStatement.executeUpdate();
	        if (rowsUpdated == 0) {
	            throw new PetShopException("Nessun prodotto trovato con l'ID specificato.");
	        }
	    } catch (SQLException e) {
	        throw new PetShopException("Errore Server", e);
	    }
	}


}
