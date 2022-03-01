package airlin.model.dao;

import java.util.List;

import airlin.model.dao.mysql.MySQLDAOException;

public abstract interface DAO<T, K> {

	void insert(T t) throws MySQLDAOException;

	List<T> getAll() throws MySQLDAOException;

	void update(T t) throws MySQLDAOException;

	void delete(T t) throws MySQLDAOException;

	T get(K id) throws MySQLDAOException;
}