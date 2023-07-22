package it.petshop.utility;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

public class DatabaseUtil {
	
	public static final String SELECT_ALL_FROM = "SELECT * FROM ";
	public static final String WHERE = " WHERE ";
	public static final String AND = " AND ";

	private DatabaseUtil() {
		throw new IllegalStateException("Utility class");
	}

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
		}

		return generatedId;
	}

}
