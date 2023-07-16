package it.petshop.utility;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

public class DatabaseUtil {

	public static int getAutoIncrementValue(PreparedStatement preparedStatement) throws PetShopException {
		int generatedId = -1;

		try {
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows > 0) {
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						generatedId = generatedKeys.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			throw new PetShopException("Errore durante il recupero dell'ID generato", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
		} finally {
			if (generatedId == -1)
				throw new PetShopException("Errore durante il recupero dell'ID generato", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

		return generatedId;
	}

}
