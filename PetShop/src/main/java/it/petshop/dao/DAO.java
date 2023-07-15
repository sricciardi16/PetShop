package it.petshop.dao;

import java.sql.SQLException;
import java.util.List;

import it.petshop.utility.PetShopException;

public interface DAO<T> {

	public void create(T bean) ;

	public boolean delete(int code) ;

	public T retrieveByKey(int code);

	public List<T> retrieveAll(String order) ;
}
