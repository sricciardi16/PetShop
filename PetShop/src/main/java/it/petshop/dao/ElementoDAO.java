package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.dto.Elemento;
import it.petshop.utility.PetShopException;

public class ElementoDAO implements DAO<Elemento> {

	private DataSource dataSource;
	private static final String TABLE_NAME = "elemento";

	public ElementoDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public synchronized void create(Elemento elemento) throws PetShopException {
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (nome, descrizione, prezzo, immagine, quantita, id_prodotto, id_ordine) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setString(1, elemento.getNome());
			preparedStatement.setString(2, elemento.getDescrizione());
			preparedStatement.setDouble(3, elemento.getPrezzo());
			preparedStatement.setString(4, elemento.getImmagine());
			preparedStatement.setInt(5, elemento.getQuantita());
			preparedStatement.setInt(6, elemento.getIdProdotto());
			preparedStatement.setInt(7, elemento.getIdOrdine());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore durante la creazione dell'elemento", 500, e);
		}
	}

	public synchronized int createAndGetId(Elemento elemento) throws PetShopException {
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (nome, descrizione, prezzo, immagine, quantita, id_prodotto, id_ordine) VALUES (?, ?, ?, ?, ?, ?, ?)";
		int generatedId = -1;

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, elemento.getNome());
			preparedStatement.setString(2, elemento.getDescrizione());
			preparedStatement.setDouble(3, elemento.getPrezzo());
			preparedStatement.setString(4, elemento.getImmagine());
			preparedStatement.setInt(5, elemento.getQuantita());
			preparedStatement.setInt(6, elemento.getIdProdotto());
			preparedStatement.setInt(7, elemento.getIdOrdine());

			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows > 0) {
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						generatedId = generatedKeys.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante la creazione dell'elemento", 500, e);
		}

		return generatedId;
	}

	@Override
	public synchronized boolean delete(int id) throws PetShopException {
		int result = 0;
		String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore durante l'eliminazione dell'elemento", 500, e);
		}

		return result != 0;
	}

	@Override
	public synchronized List<Elemento> retrieveAll(String order) throws PetShopException {
		List<Elemento> elementi = new ArrayList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL); ResultSet rs = preparedStatement.executeQuery()) {

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
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero degli elementi", 500, e);
		}

		return elementi;
	}

	public synchronized List<Elemento> retrieveAll() throws PetShopException {
		return retrieveAll("");
	}

	@Override
	public synchronized Elemento retrieveByKey(int id) throws PetShopException {
		Elemento bean = new Elemento();
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
					bean.setQuantita(rs.getInt("quantita"));
					bean.setIdProdotto(rs.getInt("id_prodotto"));
					bean.setIdOrdine(rs.getInt("id_ordine"));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero dell'elemento", 500, e);
		}

		return bean;
	}

	public synchronized void setImmagine(int id, String immagine) throws PetShopException {
		String updateSQL = "UPDATE " + TABLE_NAME + " SET immagine = ? WHERE id = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
			preparedStatement.setString(1, immagine);
			preparedStatement.setInt(2, id);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore durante l'impostazione dell'immagine", 500, e);
		}
	}
}
