package airlin.model.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import airlin.model.Cliente;
import airlin.model.dao.ClienteDAO;

public class MySQLClienteDAO implements ClienteDAO {

	private static final String TEMPLATE_INSERT = "INSERT INTO `cliente` (`nombre`,`apellidos`,`dni`,`telefono`) VALUES ( ?, ?, ?, ?);";
	private static final String TEMPLATE_UPDATE = "UPDATE `cliente` SET `nombre`=?,`apellidos`=?,`dni`=?,`telefono`=? WHERE `cliente`.`id_cliente`=?;";
	private static final String TEMPLATE_SELECT_ID = "SELECT * from `cliente` WHERE `cliente`.`id_cliente`=?;";
	private static final String TEMPLATE_SELECT_ALL = "SELECT * from `cliente`;";
	private static final String TEMPLATE_DELETE = "DELETE FROM `cliente` WHERE `cliente`.`id_cliente`=?;";

	private Connection connection;

	public MySQLClienteDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Cliente t) throws MySQLDAOException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE_INSERT,
				Statement.RETURN_GENERATED_KEYS);

		) {
			preparedStatement.setString(1, t.getNombre());
			preparedStatement.setString(2, t.getApellidos());
			preparedStatement.setString(3, t.getDni());
			preparedStatement.setString(4, t.getTelefono());
			preparedStatement.executeUpdate();

			ResultSet keys = preparedStatement.getGeneratedKeys();
			keys.next();
			t.setId(keys.getLong(1));

		} catch (SQLException e) {
			throw new MySQLDAOException("No se pudo insertar el Cliente.");
		}
	}

	@Override
	public List<Cliente> getAll() throws MySQLDAOException {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE_SELECT_ALL);

		) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				clientes.add(new Cliente(rs.getLong("id_cliente"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("dni"), rs.getString("telefono")));
			}
		} catch (Exception e) {
			throw new MySQLDAOException("No se pudo leer la lista Clientes.");
		}
		return clientes;
	}

	@Override
	public void update(Cliente cliente) throws MySQLDAOException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE_UPDATE);

		) {
			preparedStatement.setString(1, cliente.getNombre());
			preparedStatement.setString(2, cliente.getApellidos());
			preparedStatement.setString(3, cliente.getDni());
			preparedStatement.setString(4, cliente.getTelefono());
			preparedStatement.setLong(5, cliente.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new MySQLDAOException("No se pudo actualizar el Cliente.");
		}
	}

	@Override
	public void delete(Cliente t) throws MySQLDAOException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE_DELETE);

		) {
			preparedStatement.setLong(1, t.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new MySQLDAOException("No se pudo eliminar el Cliente.");
		}
	}

	@Override
	public Cliente get(Long id) throws MySQLDAOException {

		Cliente cliente = null;
		try (PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE_SELECT_ID);

		) {
			preparedStatement.setLong(1, id);

			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			cliente = new Cliente(rs.getLong("id_cliente"), rs.getString("nombre"), rs.getString("apellidos"),
					rs.getString("dni"), rs.getString("telefono"));
		} catch (Exception e) {
			throw new MySQLDAOException("No se pudo leer el Cliente.");
		}
		return cliente;
	}
}
