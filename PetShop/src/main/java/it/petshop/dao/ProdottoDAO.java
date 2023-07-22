package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.dto.Categoria;
import it.petshop.dto.Prodotto;
import it.petshop.utility.PetShopException;

public class ProdottoDAO {

	private DataSource dataSource;
	private static final String TABLE_NAME = "prodotto";
	private static final String COLUMN_NAME_ID = "id";
	private static final String COLUMN_NAME_NOME = "nome";
	private static final String COLUMN_NAME_DESCRIZIONE = "descrizione";
	private static final String COLUMN_NAME_PREZZO = "prezzo";
	private static final String COLUMN_NAME_IMMAGINE = "immagine";
	private static final String COLUMN_NAME_IN_MAGAZZINO = "in_magazzino";
	private static final String COLUMN_NAME_ID_CATEGORIA = "id_categoria";

	public ProdottoDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public synchronized void save(Prodotto prodotto) throws PetShopException {
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME_NOME + ", " + COLUMN_NAME_DESCRIZIONE + ", " + COLUMN_NAME_PREZZO + ", " + COLUMN_NAME_IMMAGINE + ", " + COLUMN_NAME_IN_MAGAZZINO + ", " + COLUMN_NAME_ID_CATEGORIA + ") VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setString(1, prodotto.getNome());
			preparedStatement.setString(2, prodotto.getDescrizione());
			preparedStatement.setDouble(3, prodotto.getPrezzo());
			preparedStatement.setString(4, prodotto.getImmagine());
			preparedStatement.setInt(5, prodotto.getInMagazzino());
			preparedStatement.setInt(6, prodotto.getIdCategoria());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore durante la creazione del prodotto", 500, e);
		}
	}

	public synchronized boolean deleteById(int id) throws PetShopException {
		int result = 0;
		String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_ID + " = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore durante l'eliminazione del prodotto", 500, e);
		}

		return result != 0;
	}

	public synchronized Prodotto findById(int id) throws PetShopException {
		Prodotto bean = new Prodotto();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_ID + " = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setInt(1, id);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					bean.setId(rs.getInt(COLUMN_NAME_ID));
					bean.setNome(rs.getString(COLUMN_NAME_NOME));
					bean.setDescrizione(rs.getString(COLUMN_NAME_DESCRIZIONE));
					bean.setPrezzo(rs.getDouble(COLUMN_NAME_PREZZO));
					bean.setImmagine(rs.getString(COLUMN_NAME_IMMAGINE));
					bean.setInMagazzino(rs.getInt(COLUMN_NAME_IN_MAGAZZINO));
					bean.setIdCategoria(rs.getInt(COLUMN_NAME_ID_CATEGORIA));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero del prodotto", 500, e);
		}

		return bean;
	}

	public synchronized int findIdByIdElemento(int idElemento) throws PetShopException {
		int prodottoId = -1;
		String selectSQL = "SELECT prodotto.id FROM prodotto JOIN elemento ON prodotto.id = elemento.id_prodotto WHERE elemento.id = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
			preparedStatement.setInt(1, idElemento);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				if (rs.next()) {
					prodottoId = rs.getInt(COLUMN_NAME_ID);
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero dell'ID del prodotto tramite ID dell'elemento", 500, e);
		}

		return prodottoId;
	}

	public synchronized List<Prodotto> findAllByCategoriaWithLimit(Categoria categoria, int limit, int offset, String order, boolean asc) throws PetShopException {
		List<Prodotto> prodotti = new ArrayList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME + ", categoria WHERE " + COLUMN_NAME_ID_CATEGORIA + " = categoria.id AND animale = ? AND " + COLUMN_NAME_IN_MAGAZZINO + " != 0 ";

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
					bean.setId(rs.getInt(COLUMN_NAME_ID));
					bean.setNome(rs.getString(COLUMN_NAME_NOME));
					bean.setDescrizione(rs.getString(COLUMN_NAME_DESCRIZIONE));
					bean.setPrezzo(rs.getDouble(COLUMN_NAME_PREZZO));
					bean.setImmagine(rs.getString(COLUMN_NAME_IMMAGINE));
					bean.setInMagazzino(rs.getInt(COLUMN_NAME_IN_MAGAZZINO));
					bean.setIdCategoria(rs.getInt(COLUMN_NAME_ID_CATEGORIA));

					prodotti.add(bean);
				}
			}
		} catch (Exception e) {
			throw new PetShopException("Errore durante il recupero dei prodotti", 500, e);
		}

		return prodotti;
	}

	public synchronized int countPagine(Categoria categoria, int limit) throws PetShopException {
		int result = 0;

		String countSQL = "SELECT COUNT(*) AS count FROM " + TABLE_NAME + ", " + CategoriaDAO.TABLE_NAME + " WHERE " + COLUMN_NAME_ID_CATEGORIA + " = " + CategoriaDAO.TABLE_NAME + "." + CategoriaDAO.COLUMN_NAME_ID + "AND animale = ?";
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
			throw new PetShopException("Errore durante il calcolo del numero di pagine", 500, e);
		}

		return result;
	}

	public synchronized void updateInMagazzino(int idProdotto, int quantita) throws PetShopException {
		String updateSQL = "UPDATE " + TABLE_NAME + " SET " + COLUMN_NAME_IN_MAGAZZINO + " = " + COLUMN_NAME_IN_MAGAZZINO + " - ? WHERE " + COLUMN_NAME_ID + " = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
			preparedStatement.setInt(1, quantita);
			preparedStatement.setInt(2, idProdotto);

			int rowsUpdated = preparedStatement.executeUpdate();
			if (rowsUpdated == 0) {
				throw new PetShopException("Nessun prodotto trovato con l'ID specificato.", 404);
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante l'aggiornamento del prodotto", 500, e);
		}
	}
	
	public synchronized void updateById(Prodotto prodotto, int id) throws PetShopException {
	    String updateSQL = "UPDATE " + TABLE_NAME + " SET " + COLUMN_NAME_NOME + " = ?, " + COLUMN_NAME_DESCRIZIONE + " = ?, " + COLUMN_NAME_PREZZO + " = ?, " + COLUMN_NAME_IMMAGINE + " = ?, " + COLUMN_NAME_IN_MAGAZZINO + " = ? WHERE " + COLUMN_NAME_ID + " = ?";

	    try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
	        preparedStatement.setString(1, prodotto.getNome());
	        preparedStatement.setString(2, prodotto.getDescrizione());
	        preparedStatement.setDouble(3, prodotto.getPrezzo());
	        preparedStatement.setString(4, prodotto.getImmagine());
	        preparedStatement.setInt(5, prodotto.getInMagazzino());
	        preparedStatement.setInt(6, id);

	        int rowsUpdated = preparedStatement.executeUpdate();
	        if (rowsUpdated == 0) {
	            throw new PetShopException("Nessun prodotto trovato con l'ID specificato.", 404);
	        }
	    } catch (SQLException e) {
	        throw new PetShopException("Errore durante l'aggiornamento del prodotto", 500, e);
	    }
	}
	
	public synchronized List<Prodotto> findFirstLimitByNomeLike(String name, int limit) throws PetShopException {
	    List<Prodotto> products = new ArrayList<>();
	    String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_NOME + " LIKE ? LIMIT ?";
	    try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
	        preparedStatement.setString(1, "%" + name + "%");
	        preparedStatement.setInt(2, limit);
	        try (ResultSet rs = preparedStatement.executeQuery()) {
	            while (rs.next()) {
	                Prodotto prodotto = new Prodotto();
	                prodotto.setId(rs.getInt(COLUMN_NAME_ID));
	                prodotto.setNome(rs.getString(COLUMN_NAME_NOME));
	                prodotto.setDescrizione(rs.getString(COLUMN_NAME_DESCRIZIONE));
	                prodotto.setPrezzo(rs.getDouble(COLUMN_NAME_PREZZO));
	                prodotto.setImmagine(rs.getString(COLUMN_NAME_IMMAGINE));
	                prodotto.setInMagazzino(rs.getInt(COLUMN_NAME_IN_MAGAZZINO));
	                prodotto.setIdCategoria(rs.getInt(COLUMN_NAME_ID_CATEGORIA));
	                products.add(prodotto);
	            }
	        }
	    } catch (SQLException e) {
	        throw new PetShopException("Errore durante il recupero dei prodotti", 500, e);
	    }
	    return products;
	}
}
