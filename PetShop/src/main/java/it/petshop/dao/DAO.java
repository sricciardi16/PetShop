package it.petshop.dao;

import java.sql.SQLException;
import java.util.List;

import it.petshop.utility.PetShopException;

public interface DAO<T> {

	public void create(T bean) throws PetShopException;

	public boolean delete(int code) throws PetShopException;

	public T retrieveByKey(int code) throws PetShopException;

	public List<T> retrieveAll(String order) throws PetShopException;
}
