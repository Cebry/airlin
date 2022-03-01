package airlin.model.dao.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import airlin.model.dao.BilleteDAO;
import airlin.model.dao.ClienteDAO;
import airlin.model.dao.DAOManager;
import airlin.model.dao.FacturaDAO;

public class MySQLDAOManager implements DAOManager {

	private static final String LOGIN = "phpmyadmin";
	private static final String PASS = "admin";
	private static final String URL = "jdbc:mysql://localhost:3306/HLC-U2-A9?useAffectedRows=true";

	static private Connection connection;

	static private ClienteDAO cliente = null;
	static private FacturaDAO factura = null;
	static private BilleteDAO billete = null;

	public MySQLDAOManager() throws SQLException {
		connection = DriverManager.getConnection(URL, LOGIN, PASS);
	}

	@Override
	public ClienteDAO getClienteDAO() {
		if (cliente == null) {
			cliente = new MySQLClienteDAO(connection);
		}
		return cliente;
	}

	@Override
	public FacturaDAO getFacturaDAO() {
		if (factura == null) {
			factura = new MySQLFacturaDAO(connection);
		}
		return factura;
	}

	@Override
	public BilleteDAO getBilleteDAO() {
		if (billete == null) {
			billete = new MySQLBilleteDAO(connection);
		}
		return billete;
	}

	@Override
	public void iniciarTransaccion() throws MySQLDAOException {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new MySQLDAOException("No se inició correctamente la transacción.");
		}

	}

	@Override
	public void acabarTransaccion() throws MySQLDAOException {
		try {
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			throw new MySQLDAOException("No se acabó correctamente la transacción.");
		}

	}

	@Override
	public void commitear() throws MySQLDAOException {
		try {
			connection.commit();
		} catch (SQLException e) {
			throw new MySQLDAOException("No se acometió correctamente la transacción.");
		}
	}

	@Override
	public void rollback() throws MySQLDAOException {
		try {
			connection.rollback();
		} catch (SQLException e) {
			throw new MySQLDAOException("No se ejecutó el rollback correctamente.");
		}

	}
}
