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
import it.petshop.utility.DatabaseUtil;
import it.petshop.utility.PetShopException;

public class ElementoDAO {

	private DataSource dataSource;
	private static final String TABLE_NAME = "elemento";
	private static final String COLUMN_NAME_ID = "id";
	private static final String COLUMN_NAME_NOME = "nome";
	private static final String COLUMN_NAME_DESCRIZIONE = "descrizione";
	private static final String COLUMN_NAME_PREZZO = "prezzo";
	private static final String COLUMN_NAME_IMMAGINE = "immagine";
	private static final String COLUMN_NAME_QUANTITA = "quantita";
	private static final String COLUMN_NAME_ID_PRODOTTO = "id_prodotto";
	private static final String COLUMN_NAME_ID_ORDINE = "id_ordine";

	public ElementoDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public synchronized int save(Elemento elemento) throws PetShopException {
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME_NOME + ", " + COLUMN_NAME_DESCRIZIONE + ", " + COLUMN_NAME_PREZZO + ", " + COLUMN_NAME_IMMAGINE + ", " + COLUMN_NAME_QUANTITA + ", " + COLUMN_NAME_ID_PRODOTTO + ", " + COLUMN_NAME_ID_ORDINE + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
		int generatedId = -1;

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, elemento.getNome());
			preparedStatement.setString(2, elemento.getDescrizione());
			preparedStatement.setDouble(3, elemento.getPrezzo());
			preparedStatement.setString(4, elemento.getImmagine());
			preparedStatement.setInt(5, elemento.getQuantita());
			preparedStatement.setInt(6, elemento.getIdProdotto());
			preparedStatement.setInt(7, elemento.getIdOrdine());

			generatedId = DatabaseUtil.getAutoIncrementValue(preparedStatement);

		} catch (SQLException e) {
			throw new PetShopException("Errore durante la creazione dell'elemento", 500, e);
		}

		return generatedId;
	}

	public synchronized List<Elemento> findAllByIdOrdine(int idOrdine) throws PetShopException {
		List<Elemento> elementi = new ArrayList<>();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_ID_ORDINE + " = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setInt(1, idOrdine);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					Elemento bean = new Elemento();
					bean.setId(rs.getInt(COLUMN_NAME_ID));
					bean.setNome(rs.getString(COLUMN_NAME_NOME));
					bean.setDescrizione(rs.getString(COLUMN_NAME_DESCRIZIONE));
					bean.setPrezzo(rs.getDouble(COLUMN_NAME_PREZZO));
					bean.setImmagine(rs.getString(COLUMN_NAME_IMMAGINE));
					bean.setQuantita(rs.getInt(COLUMN_NAME_QUANTITA));
					bean.setIdProdotto(rs.getInt(COLUMN_NAME_ID_PRODOTTO));
					bean.setIdOrdine(rs.getInt(COLUMN_NAME_ID_ORDINE));

					elementi.add(bean);
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero degli elementi dell'ordine", 500, e);
		}

		return elementi;
	}

	public synchronized void updateImmagineById(int id, String immagine) throws PetShopException {
		String updateSQL = "UPDATE " + TABLE_NAME + " SET " + COLUMN_NAME_IMMAGINE + " = ? WHERE " + COLUMN_NAME_ID + " = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
			preparedStatement.setString(1, immagine);
			preparedStatement.setInt(2, id);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PetShopException("Errore durante l'impostazione dell'immagine", 500, e);
		}
	}
}
