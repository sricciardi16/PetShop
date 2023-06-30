package it.petshop.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
	
	public void create(T bean) throws SQLException;

	public boolean delete(int code) throws SQLException;

	public T retrieveByKey(int code) throws SQLException;
	
	public List<T> retrieveAll(String order) throws SQLException;
}
