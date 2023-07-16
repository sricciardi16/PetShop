package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.dto.MetodoSpedizione;
import it.petshop.utility.PetShopException;

public class MetodoSpedizioneDAO implements DAO<MetodoSpedizione> {

	private DataSource dataSource;
	private static final String TABLE_NAME = "metodo_spedizione";

	public MetodoSpedizioneDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public synchronized void create(MetodoSpedizione metodoSpedizione) throws PetShopException {
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (descrizione, prezzo, giorni_consegna_previsti) VALUES (?, ?, ?)";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setString(1, metodoSpedizione.getDescrizione());
			preparedStatement.setDouble(2, metodoSpedizione.getPrezzo());
			preparedStatement.setInt(3, metodoSpedizione.getGiorniConsegnaPrevisti());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore durante la creazione del metodo di spedizione", 500, e);
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
			throw new PetShopException("Errore durante l'eliminazione del metodo di spedizione", 500, e);
		}

		return result != 0;
	}

	@Override
	public synchronized List<MetodoSpedizione> retrieveAll(String order) throws PetShopException {
		List<MetodoSpedizione> metodiSpedizione = new ArrayList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL); ResultSet rs = preparedStatement.executeQuery()) {

			while (rs.next()) {
				MetodoSpedizione bean = new MetodoSpedizione();
				bean.setId(rs.getInt("id"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setPrezzo(rs.getDouble("prezzo"));
				bean.setGiorniConsegnaPrevisti(rs.getInt("giorni_consegna_previsti"));

				metodiSpedizione.add(bean);
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero dei metodi di spedizione", 500, e);
		}

		return metodiSpedizione;
	}

	public synchronized List<MetodoSpedizione> retrieveAll() throws PetShopException {
		return retrieveAll("");
	}

	@Override
	public synchronized MetodoSpedizione retrieveByKey(int id) throws PetShopException {
		MetodoSpedizione bean = new MetodoSpedizione();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setInt(1, id);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					bean.setId(rs.getInt("id"));
					bean.setDescrizione(rs.getString("descrizione"));
					bean.setPrezzo(rs.getDouble("prezzo"));
					bean.setGiorniConsegnaPrevisti(rs.getInt("giorni_consegna_previsti"));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero del metodo di spedizione", 500, e);
		}

		return bean;
	}

}
