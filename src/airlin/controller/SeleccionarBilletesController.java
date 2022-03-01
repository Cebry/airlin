package airlin.controller;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import airlin.model.Billete;
import airlin.model.Cliente;
import airlin.model.Factura;
import airlin.model.dao.DAOManager;
import airlin.model.dao.mysql.MySQLDAOException;
import airlin.model.dao.mysql.MySQLDAOManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SeleccionarBilletesController implements Initializable {

	@FXML
	private TableColumn<Billete, Long> colPlaza;

	@FXML
	private TableColumn<Billete, Float> colPrecio;

	@FXML
	private TableColumn<Billete, Long> colId;

	@FXML
	private TableColumn<Billete, Date> colFecha;

	@FXML
	private TableView<Billete> tabla;

	@FXML
	private Button buttonActualizar;

	@FXML
	private Button buttonSeleccionar;

	@FXML
	private Button buttonCancelar;

	DAOManager daoManager = null;

	ObservableList<Billete> lista = FXCollections.observableArrayList();

	Billete billete = null;

	private Cliente cliente = null;

	void initCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@FXML
	void aceptar() {

		ArrayList<Billete> billetes = new ArrayList<Billete>();
		billetes.addAll(tabla.getSelectionModel().getSelectedItems());

		try {
			daoManager.iniciarTransaccion();
			try {
				Factura factura = new Factura(LocalDate.now(), cliente.getId());
				daoManager.getFacturaDAO().insert(factura);
				for (Billete i : billetes) {
					if (daoManager.getBilleteDAO().get(i.getId()) == null) {
						throw new Exception("Este billete ya no está disponible");
					}
					i.setIdFactura(factura.getId());
					daoManager.getBilleteDAO().update(i);
				}
				daoManager.commitear();
			} catch (Exception e) {
				daoManager.rollback();
				new Alerta(e.getMessage()).showAndWait();
			}
			daoManager.acabarTransaccion();
		} catch (MySQLDAOException e1) {
			new Alerta(e1.getMessage()).showAndWait();
		}
		cancelar();
	}

	@FXML
	void actualizarTabla() {
		lista.clear();
		try {
			lista.addAll(daoManager.getBilleteDAO().getAll().stream()
					.filter(x -> x.getIdFactura() == null && x.getFecha().isAfter(LocalDate.now()))
					.collect(Collectors.toList()));
		} catch (Exception e) {
			new Alerta(e.getMessage()).showAndWait();
		}
		tabla.setItems(lista);
	}

	@FXML
	void cancelar() {
		Stage stage = (Stage) buttonCancelar.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			daoManager = (DAOManager) new MySQLDAOManager();

		} catch (SQLException e) {
			new Alerta(e.getMessage()).showAndWait();
		}

		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colPlaza.setCellValueFactory(new PropertyValueFactory<>("plaza"));
		colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

		tabla.setPlaceholder(new Label("No hay billetes disponibles."));

		tabla.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		actualizarTabla();
	}
}
