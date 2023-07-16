package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.petshop.dto.Categoria;
import it.petshop.utility.PetShopException;

public class CategoriaDAO {

	private DataSource dataSource;
	private static final String TABLE_NAME = "categoria";

	public CategoriaDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public synchronized List<Categoria> findAllByCategoria(Categoria categoria) throws PetShopException {
		List<Categoria> categorie = new ArrayList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE animale = ?";

		if (!categoria.getTipologia().isBlank())
			selectSQL += " AND tipologia = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setString(1, categoria.getAnimale());

			if (!categoria.getTipologia().isBlank())
				preparedStatement.setString(2, categoria.getTipologia());

			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					Categoria bean = new Categoria();
					bean.setId(rs.getInt("id"));
					bean.setAnimale(rs.getString("animale"));
					bean.setTipologia(rs.getString("tipologia"));
					bean.setTipologiaIn(rs.getString("tipologia_in"));

					categorie.add(bean);
				}
			}
		} catch (Exception e) {
			throw new PetShopException("Errore durante il recupero delle categorie", 500, e);
		}

		return categorie;
	}

}
