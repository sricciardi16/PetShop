package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.dto.Indirizzo;
import it.petshop.dto.Utente;
import it.petshop.utility.PetShopException;

public class IndirizzoDAO {

	private DataSource dataSource;
	private static final String TABLE_NAME = "indirizzo";
	private static final String COLUMN_NAME_ID = "id";
	private static final String COLUMN_NAME_ALIAS = "alias";
	private static final String COLUMN_NAME_VIA = "via";
	private static final String COLUMN_NAME_NUMERO = "numero";
	private static final String COLUMN_NAME_CITTA = "citta";
	private static final String COLUMN_NAME_CODICE_POSTALE = "codice_postale";
	private static final String COLUMN_NAME_PROVINCIA = "provincia";
	private static final String COLUMN_NAME_PAESE = "paese";
	private static final String COLUMN_NAME_ID_UTENTE = "id_utente";

	public IndirizzoDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public synchronized void save(Indirizzo indirizzo) throws PetShopException {
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME_ALIAS + ", " + COLUMN_NAME_VIA + ", " + COLUMN_NAME_NUMERO + ", " + COLUMN_NAME_CITTA + ", " + COLUMN_NAME_CODICE_POSTALE + ", " + COLUMN_NAME_PROVINCIA + ", " + COLUMN_NAME_PAESE + ", " + COLUMN_NAME_ID_UTENTE + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setString(1, indirizzo.getAlias());
			preparedStatement.setString(2, indirizzo.getVia());
			preparedStatement.setString(3, indirizzo.getNumero());
			preparedStatement.setString(4, indirizzo.getCitta());
			preparedStatement.setString(5, indirizzo.getCodicePostale());
			preparedStatement.setString(6, indirizzo.getProvincia());
			preparedStatement.setString(7, indirizzo.getPaese());
			preparedStatement.setInt(8, indirizzo.getIdUtente());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore durante la creazione dell'indirizzo", 500, e);
		}
	}

	public synchronized boolean deleteById(int id) throws PetShopException {
		int result = 0;
		String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_ID + " = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore durante l'eliminazione dell'indirizzo", 500, e);
		}

		return result != 0;
	}

	public synchronized List<Indirizzo> findAllByUtente(Utente utente) throws PetShopException {
		List<Indirizzo> indirizzi = new ArrayList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_ID_UTENTE + " = ? ORDER BY " + COLUMN_NAME_ID + " DESC";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setInt(1, utente.getId());

			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					Indirizzo bean = new Indirizzo();
					bean.setId(rs.getInt(COLUMN_NAME_ID));
					bean.setAlias(rs.getString(COLUMN_NAME_ALIAS));
					bean.setVia(rs.getString(COLUMN_NAME_VIA));
					bean.setNumero(rs.getString(COLUMN_NAME_NUMERO));
					bean.setCitta(rs.getString(COLUMN_NAME_CITTA));
					bean.setCodicePostale(rs.getString(COLUMN_NAME_CODICE_POSTALE));
					bean.setProvincia(rs.getString(COLUMN_NAME_PROVINCIA));
					bean.setPaese(rs.getString(COLUMN_NAME_PAESE));
					bean.setIdUtente(rs.getInt(COLUMN_NAME_ID_UTENTE));

					indirizzi.add(bean);
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero degli indirizzi dell'utente", 500, e);
		}

		return indirizzi;
	}
}
