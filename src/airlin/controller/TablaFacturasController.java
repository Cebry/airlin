package airlin.controller;

import java.net.URL;
import java.util.ResourceBundle;

import airlin.model.Cliente;
import airlin.model.Factura;
import airlin.model.dao.DAOManager;
import airlin.model.dao.mysql.MySQLDAOManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TablaFacturasController implements Initializable {

	@FXML
	private Button buttonActualizar;

	@FXML
	private Button buttonActualizarTabla;

	@FXML
	private Button buttonBorrar;

	@FXML
	private Button buttonInsertar;

	@FXML
	private Button buttonLimpiar;

	@FXML
	private TableColumn<Cliente, String> colApellidos;

	@FXML
	private TableColumn<Cliente, String> colDni;

	@FXML
	private TableColumn<Cliente, Long> colId1;

	@FXML
	private TableColumn<Cliente, String> colNombre;

	@FXML
	private TableColumn<Cliente, String> colTelefono;

	@FXML
	private TableColumn<Factura, Long> colId;

	@FXML
	private TableColumn<Factura, Long> colFecha;

	@FXML
	private DatePicker pickerFecha;

	@FXML
	private TableView<Factura> tabla;

	@FXML
	private TableView<Cliente> tabla1;

	DAOManager daoManager = null;

	ObservableList<Factura> lista = FXCollections.observableArrayList();
	ObservableList<Cliente> lista1 = FXCollections.observableArrayList();

	Factura factura = null;

	Cliente cliente = null;

	TableViewSelectionModel<Factura> listaSeleccionada = null;

	@FXML
	void actualizarFactura() {
		factura = new Factura(factura.getId(), pickerFecha.getValue(), cliente.getId());
		try {
			daoManager.getFacturaDAO().update(factura);
		} catch (Exception e) {
			new Alerta(e.getMessage()).showAndWait();
		}
		actualizarTabla();
		limpiarFormulario();
	}

	@FXML
	void actualizarTabla() {
		lista.clear();
		try {
			lista.addAll(daoManager.getFacturaDAO().getAll());
		} catch (Exception e) {
			new Alerta(e.getMessage()).showAndWait();
		}
		tabla.setItems(lista);
	}

	void actualizarTabla1() {
		lista1.clear();
		lista1.add(cliente);
		tabla1.setItems(lista1);
	}

	@FXML
	void borrarFactura() {

		try {
			daoManager.getFacturaDAO().delete(factura);
		} catch (Exception e) {
			new Alerta(e.getMessage()).showAndWait();
		}
		actualizarTabla();
		limpiarFormulario();
	}

	@FXML
	void insertarFactura() {

		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/airlin/view/SeleccionarCliente.fxml"));

			Scene scene = new Scene(loader.load());
			stage = new Stage();
			stage.setTitle("Airlin");

			stage.setResizable(false);

			stage.setScene(scene);
			stage.showAndWait();
		} catch (Exception e) {
			new Alerta(e.getMessage()).showAndWait();
		}
	}

	@FXML
	void limpiarFormulario() {

		cliente = null;
		actualizarTabla1();
		pickerFecha.setValue(null);

		buttonInsertar.setDisable(false);
		buttonBorrar.setDisable(true);
		buttonActualizar.setDisable(true);
	}

	@FXML
	void seleccionarTupla() {
		if (!lista.isEmpty()) {
			factura = tabla.getSelectionModel().getSelectedItem();
			try {
				cliente = daoManager.getClienteDAO().get(factura.getIdCliente());
			} catch (Exception e) {
				new Alerta(e.getMessage()).showAndWait();
			}
			actualizarFormulario();

			buttonInsertar.setDisable(true);
			buttonBorrar.setDisable(false);
			buttonActualizar.setDisable(false);
		}
	}

	private void actualizarFormulario() {
		actualizarTabla1();
		pickerFecha.setValue(factura.getFecha());

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			daoManager = (DAOManager) new MySQLDAOManager();

		} catch (Exception e) {
			new Alerta(e.getMessage()).showAndWait();
		}
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

		colId1.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
		colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
		colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

		tabla.setPlaceholder(new Label("No hay facturas aún."));
		tabla1.setPlaceholder(new Label("No se ha seleccionado cliente."));

		listaSeleccionada = tabla.getSelectionModel();

		listaSeleccionada.setSelectionMode(SelectionMode.SINGLE);

		actualizarTabla();
		limpiarFormulario();

	}
}