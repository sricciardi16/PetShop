package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.dto.Categoria;
import it.petshop.utility.PetShopException;

import static it.petshop.utility.DatabaseUtil.*;

public class CategoriaDAO {

	private DataSource dataSource;
	public static final String TABLE_NAME = "categoria";
	public static final String COLUMN_NAME_ID = "id";
	private static final String COLUMN_NAME_ANIMALE = "animale";
	private static final String COLUMN_NAME_TIPOLOGIA = "tipologia";
	private static final String COLUMN_NAME_TIPOLOGIA_IN = "tipologia_in";

	public CategoriaDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public synchronized List<Categoria> findAllByCategoria(Categoria categoria) throws PetShopException {
		List<Categoria> categorie = new ArrayList<>();

		String selectSQL = SELECT_ALL_FROM + TABLE_NAME + WHERE + COLUMN_NAME_ANIMALE + " = ?";

		if (!categoria.getTipologia().isBlank())
			selectSQL += " AND " + COLUMN_NAME_TIPOLOGIA + " = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setString(1, categoria.getAnimale());

			if (!categoria.getTipologia().isBlank())
				preparedStatement.setString(2, categoria.getTipologia());

			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					Categoria bean = new Categoria();
					bean.setId(rs.getInt(COLUMN_NAME_ID));
					bean.setAnimale(rs.getString(COLUMN_NAME_ANIMALE));
					bean.setTipologia(rs.getString(COLUMN_NAME_TIPOLOGIA));
					bean.setTipologiaIn(rs.getString(COLUMN_NAME_TIPOLOGIA_IN));

					categorie.add(bean);
				}
			}
		} catch (Exception e) {
			throw new PetShopException("Errore durante il recupero delle categorie", 500, e);
		}

		return categorie;
	}

	public synchronized int findIdByCategoria(Categoria categoria) throws PetShopException {
	    int categoriaId = -1;

	    String selectSQL = SELECT_ALL_FROM + TABLE_NAME + WHERE + COLUMN_NAME_ANIMALE + " = ? AND " + COLUMN_NAME_TIPOLOGIA + " = ? AND " + COLUMN_NAME_TIPOLOGIA_IN + " = ?";

	    try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

	        preparedStatement.setString(1, categoria.getAnimale());
	        preparedStatement.setString(2, categoria.getTipologia());
	        preparedStatement.setString(3, categoria.getTipologiaIn());

	        try (ResultSet rs = preparedStatement.executeQuery()) {
	            if (rs.next()) {
	                categoriaId = rs.getInt(COLUMN_NAME_ID);
	            }
	        }
	    } catch (Exception e) {
	        throw new PetShopException("Errore durante il recupero dell'id della categoria", 500, e);
	    }

	    return categoriaId;
	}
}
