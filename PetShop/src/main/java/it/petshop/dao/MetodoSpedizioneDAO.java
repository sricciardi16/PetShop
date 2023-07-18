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

public class MetodoSpedizioneDAO {

	private DataSource dataSource;
	private static final String TABLE_NAME = "metodo_spedizione";

	public MetodoSpedizioneDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public synchronized List<MetodoSpedizione> findAll() throws PetShopException {
		List<MetodoSpedizione> metodiSpedizione = new ArrayList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME;

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

	public synchronized MetodoSpedizione findByKey(int id) throws PetShopException {
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
