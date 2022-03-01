package airlin.controller;

import java.net.URL;
import java.util.ResourceBundle;

import airlin.model.Cliente;
import airlin.model.dao.DAOManager;
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
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TablaClientesController implements Initializable {

	@FXML
	private TableColumn<Cliente, String> colApellidos;

	@FXML
	private TableColumn<Cliente, String> colDni;

	@FXML
	private TableColumn<Cliente, Long> colId;

	@FXML
	private TableColumn<Cliente, String> colNombre;

	@FXML
	private TableColumn<Cliente, String> colTelefono;

	@FXML
	private TableView<Cliente> tabla;

	@FXML
	private Button buttonActualizar;

	@FXML
	private Button buttonBorrar;

	@FXML
	private Button buttonInsertar;

	@FXML
	private Button buttonLimpiar;

	@FXML
	private Button buttonActualizarTabla;

	@FXML
	private TextField textNombre;

	@FXML
	private TextField textApellidos;

	@FXML
	private TextField textDni;

	@FXML
	private TextField textTelefono;

	DAOManager daoManager = null;

	ObservableList<Cliente> lista = FXCollections.observableArrayList();

	Cliente cliente = null;

	TableViewSelectionModel<Cliente> listaSeleccionada = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			daoManager = (DAOManager) new MySQLDAOManager();

		} catch (Exception e) {
			new Alerta(e.getMessage()).showAndWait();
		}

		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
		colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
		colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

		tabla.setPlaceholder(new Label("No hay clientes aún."));

		listaSeleccionada = tabla.getSelectionModel();

		listaSeleccionada.setSelectionMode(SelectionMode.SINGLE);

		actualizarTabla();
		limpiarFormulario();
	}

	public void actualizarTabla() {
		lista.clear();
		try {
			lista.addAll(daoManager.getClienteDAO().getAll());
		} catch (Exception e) {
			new Alerta(e.getMessage()).showAndWait();
		}
		tabla.setItems(lista);
	}

	public void insertarCliente() {
		try {
			cliente = new Cliente(textNombre.getText(), textApellidos.getText(), textDni.getText(),
					textTelefono.getText());

			daoManager.getClienteDAO().insert(cliente);
		} catch (Exception e) {
			new Alerta(e.getMessage()).showAndWait();
		}
		actualizarTabla();
		limpiarFormulario();
	}

	public void actualizarCliente() {
		try {
			cliente = new Cliente(cliente.getId(), textNombre.getText(), textApellidos.getText(), textDni.getText(),
					textTelefono.getText());

			daoManager.getClienteDAO().update(cliente);
		} catch (Exception e) {
			new Alerta(e.getMessage()).showAndWait();
		}
		actualizarTabla();
		limpiarFormulario();
	}

	public void borrarCliente() {
		try {
			daoManager.getClienteDAO().delete(cliente);
		} catch (Exception e) {
			new Alerta(e.getMessage()).showAndWait();
		}
		actualizarTabla();
		limpiarFormulario();
	}

	public void limpiarFormulario() {

		textNombre.setText("");
		textApellidos.setText("");
		textDni.setText("");
		textTelefono.setText("");

		buttonInsertar.setDisable(false);
		buttonBorrar.setDisable(true);
		buttonActualizar.setDisable(true);
	}

	public void seleccionarTupla() {

		if (!lista.isEmpty()) {
			cliente = tabla.getSelectionModel().getSelectedItem();

			actualizarFormulario();

			buttonInsertar.setDisable(true);
			buttonBorrar.setDisable(false);
			buttonActualizar.setDisable(false);
		}
	}

	public void actualizarFormulario() {
		textNombre.setText(cliente.getNombre());
		textApellidos.setText(cliente.getApellidos());
		textDni.setText(cliente.getDni());
		textTelefono.setText(cliente.getTelefono());
	}
}
