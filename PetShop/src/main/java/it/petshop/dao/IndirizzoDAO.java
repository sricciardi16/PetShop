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

	public IndirizzoDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public synchronized void save(Indirizzo indirizzo) throws PetShopException {
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (alias, via, numero, citta, codice_postale, provincia, paese, id_utente) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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
		String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

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

		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_utente = ? ORDER BY id DESC";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setInt(1, utente.getId());

			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					Indirizzo bean = new Indirizzo();
					bean.setId(rs.getInt("id"));
					bean.setAlias(rs.getString("alias"));
					bean.setVia(rs.getString("via"));
					bean.setNumero(rs.getString("numero"));
					bean.setCitta(rs.getString("citta"));
					bean.setCodicePostale(rs.getString("codice_postale"));
					bean.setProvincia(rs.getString("provincia"));
					bean.setPaese(rs.getString("paese"));
					bean.setIdUtente(rs.getInt("id_utente"));

					indirizzi.add(bean);
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero degli indirizzi dell'utente", 500, e);
		}

		return indirizzi;
	}
}
