package airlin.model.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import airlin.model.Billete;
import airlin.model.dao.BilleteDAO;

public class MySQLBilleteDAO implements BilleteDAO {

	private static final String TEMPLATE_INSERT = "INSERT INTO `billete` (`plaza`, `precio`, `fecha`, `id_factura`) VALUES (?, ?, ?, ?);";
	private static final String TEMPLATE_UPDATE = "UPDATE `billete` SET  `plaza`=?, `precio`=?, `fecha`=?, `id_factura`=? WHERE `billete`.`id_billete`=?;";
	private static final String TEMPLATE_UPDATE_NULL_ID_FACTURA = "UPDATE `billete` SET  `plaza`=?, `precio`=?, `fecha`=? WHERE `billete`.`id_billete`=?;";
	private static final String TEMPLATE_SELECT_ALL = "SELECT * from `billete`;";
	private static final String TEMPLATE_SELECT_ID = "SELECT * from `billete` WHERE `billete`.`id_billete`=?;";
	private static final String TEMPLATE_DELETE = "DELETE FROM `billete` WHERE `billete`.`id_billete`=?;";

	private Connection connection;

	public MySQLBilleteDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Billete billete) throws MySQLDAOException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE_INSERT,
				Statement.RETURN_GENERATED_KEYS);

		) {
			preparedStatement.setLong(1, billete.getPlaza());
			preparedStatement.setFloat(2, billete.getPrecio());
			preparedStatement.setDate(3, Date.valueOf(billete.getFecha()));

			if (billete.getIdFactura() == null) {
				preparedStatement.setNull(4, Types.INTEGER);
			} else {
				preparedStatement.setLong(4, billete.getIdFactura());
			}
			preparedStatement.executeUpdate();

			ResultSet keys = preparedStatement.getGeneratedKeys();

			keys.next();
			billete.setId(keys.getLong(1));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MySQLDAOException("No se pudo insertar el Billete.");
		}
	}

	@Override
	public List<Billete> getAll() throws MySQLDAOException {
		ArrayList<Billete> billetes = new ArrayList<Billete>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE_SELECT_ALL);

		) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {

				Integer idFactura = (Integer) rs.getObject("id_factura");

				if (idFactura == null) {
					billetes.add(new Billete(rs.getLong("id_billete"), rs.getLong("plaza"), rs.getFloat("precio"),
							rs.getDate("fecha").toLocalDate(), null));
				} else {
					billetes.add(new Billete(rs.getLong("id_billete"), rs.getLong("plaza"), rs.getFloat("precio"),
							rs.getDate("fecha").toLocalDate(), rs.getLong("id_factura")));
				}
			}
		} catch (Exception e) {
			throw new MySQLDAOException("No se pudo leer la lista de Billetes.");
		}
		return billetes;
	}

	@Override
	public void update(Billete billete) throws MySQLDAOException {
		if (billete.getIdFactura() == null) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE_UPDATE_NULL_ID_FACTURA);

			) {
				preparedStatement.setLong(1, billete.getPlaza());
				preparedStatement.setFloat(2, billete.getPrecio());
				preparedStatement.setDate(3, Date.valueOf(billete.getFecha()));
				preparedStatement.setLong(4, billete.getId());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				throw new MySQLDAOException("No se pudo actualizar el Billete.");
			}
		} else {
			try (PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE_UPDATE);

			) {

				preparedStatement.setLong(1, billete.getPlaza());
				preparedStatement.setFloat(2, billete.getPrecio());
				preparedStatement.setDate(3, Date.valueOf(billete.getFecha()));
				preparedStatement.setLong(4, billete.getIdFactura());
				preparedStatement.setLong(5, billete.getId());

				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				throw new MySQLDAOException("No se pudo actualizar el Billete.");
			}
		}
	}

	@Override
	public void delete(Billete billete) throws MySQLDAOException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE_DELETE);

		) {
			preparedStatement.setLong(1, billete.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new MySQLDAOException("No se pudo eliminar el Billete.");
		}
	}

	@Override
	public Billete get(Long id) throws MySQLDAOException {
		Billete billete = null;
		try (PreparedStatement preparedStatement = connection.prepareStatement(TEMPLATE_SELECT_ID);

		) {
			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			billete = new Billete(rs.getLong("id_billete"), rs.getLong("plaza"), rs.getFloat("precio"),
					rs.getDate("fecha").toLocalDate(), rs.getLong("id_factura"));
		} catch (Exception e) {
			throw new MySQLDAOException("No se pudo leer el Billete.");

		}
		return billete;
	}

}
