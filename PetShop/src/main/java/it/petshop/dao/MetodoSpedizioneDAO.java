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

import static it.petshop.utility.DatabaseUtil.*;

public class MetodoSpedizioneDAO {

	private DataSource dataSource;
	private static final String TABLE_NAME = "metodo_spedizione";
	private static final String COLUMN_NAME_ID = "id";
	private static final String COLUMN_NAME_DESCRIZIONE = "descrizione";
	private static final String COLUMN_NAME_PREZZO = "prezzo";
	private static final String COLUMN_NAME_GIORNI_CONSEGNA_PREVISTI = "giorni_consegna_previsti";

	public MetodoSpedizioneDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public synchronized List<MetodoSpedizione> findAll() throws PetShopException {
		List<MetodoSpedizione> metodiSpedizione = new ArrayList<>();

		String selectSQL = SELECT_ALL_FROM + TABLE_NAME;

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL); ResultSet rs = preparedStatement.executeQuery()) {

			while (rs.next()) {
				MetodoSpedizione bean = new MetodoSpedizione();
				bean.setId(rs.getInt(COLUMN_NAME_ID));
				bean.setDescrizione(rs.getString(COLUMN_NAME_DESCRIZIONE));
				bean.setPrezzo(rs.getDouble(COLUMN_NAME_PREZZO));
				bean.setGiorniConsegnaPrevisti(rs.getInt(COLUMN_NAME_GIORNI_CONSEGNA_PREVISTI));

				metodiSpedizione.add(bean);
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero dei metodi di spedizione", 500, e);
		}

		return metodiSpedizione;
	}

	public synchronized MetodoSpedizione findByKey(int id) throws PetShopException {
		MetodoSpedizione bean = new MetodoSpedizione();
		String selectSQL = SELECT_ALL_FROM + TABLE_NAME + WHERE + COLUMN_NAME_ID + " = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setInt(1, id);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					bean.setId(rs.getInt(COLUMN_NAME_ID));
					bean.setDescrizione(rs.getString(COLUMN_NAME_DESCRIZIONE));
					bean.setPrezzo(rs.getDouble(COLUMN_NAME_PREZZO));
					bean.setGiorniConsegnaPrevisti(rs.getInt(COLUMN_NAME_GIORNI_CONSEGNA_PREVISTI));
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero del metodo di spedizione", 500, e);
		}

		return bean;
	}

}
