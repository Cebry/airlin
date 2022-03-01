package airlin.model.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import airlin.model.Factura;
import airlin.model.dao.FacturaDAO;

public class MySQLFacturaDAO implements FacturaDAO {

	private static final String TEMPLATE_INSERT = "INSERT INTO `factura` (`fecha`,`id_cliente`) VALUES (?, ?);";
	private static final String TEMPLATE_UPDATE = "UPDATE `factura` SET `fecha`=?,`id_cliente`=? WHERE `factura`.`id_factura`=?;";
	private static final String TEMPLATE_SELECT_ID = "SELECT * from `factura` WHERE `factura`.`id_factura`=?;";
	private static final String TEMPLATE_SELECT_ALL = "SELECT * from `factura`;";
	private static final String TEMPLATE_DELETE = "DELETE FROM `factura` WHERE `factura`.`id_factura`=?;";

	public Connection connection;

	public MySQLFacturaDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Factura factura) throws MySQLDAOException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE_INSERT,
				Statement.RETURN_GENERATED_KEYS);

		) {
			preparedStatement.setDate(1, Date.valueOf(factura.getFecha()));
			preparedStatement.setLong(2, factura.getIdCliente());

			preparedStatement.executeUpdate();

			ResultSet keys = preparedStatement.getGeneratedKeys();
			keys.next();
			factura.setId(keys.getLong(1));

		} catch (SQLException e) {
			throw new MySQLDAOException("No se pudo insertar la Factura.");
		}
	}

	@Override
	public List<Factura> getAll() {
		ArrayList<Factura> facturas = new ArrayList<Factura>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE_SELECT_ALL);

		) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Factura factura = new Factura(rs.getLong("id_factura"), rs.getDate("fecha").toLocalDate(),
						rs.getLong("id_cliente"));
				facturas.add(factura);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return facturas;
	}

	@Override
	public void update(Factura factura) throws MySQLDAOException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE_UPDATE,
				Statement.RETURN_GENERATED_KEYS);

		) {
			preparedStatement.setDate(1, Date.valueOf(factura.getFecha()));
			preparedStatement.setLong(2, factura.getIdCliente());
			preparedStatement.setLong(3, factura.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new MySQLDAOException("No se pudo actualizar la Factura.");
		}
	}

	@Override
	public void delete(Factura factura) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE_DELETE);

		) {
			preparedStatement.setLong(1, factura.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Factura get(Long id) throws MySQLDAOException {
		Factura factura = null;
		try (PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE_SELECT_ID);

		) {
			preparedStatement.setLong(1, id);

			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			factura = new Factura(rs.getLong("id_factura"), rs.getDate("fecha").toLocalDate(),
					rs.getLong("id_cliente"));
		} catch (SQLException e) {
			throw new MySQLDAOException("No se pudo leer la Factura.");
		}
		return factura;
	}

}
