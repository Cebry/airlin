package airlin.model.dao;

import airlin.model.dao.mysql.MySQLDAOException;

public interface DAOManager {

	ClienteDAO getClienteDAO();

	FacturaDAO getFacturaDAO();

	BilleteDAO getBilleteDAO();

	void iniciarTransaccion() throws MySQLDAOException;

	void acabarTransaccion() throws MySQLDAOException;

	void commitear() throws MySQLDAOException;

	void rollback() throws MySQLDAOException;

}
