package airlin.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import airlin.model.Cliente;
import airlin.model.dao.DAOManager;
import airlin.model.dao.mysql.MySQLDAOException;
import airlin.model.dao.mysql.MySQLDAOManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SeleccionarClienteController implements Initializable {

	@FXML
	private Button buttonAceptar;

	@FXML
	private Button buttonActualizarTabla;

	@FXML
	private Button buttonCancelar;

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

	DAOManager daoManager = null;

	ObservableList<Cliente> lista = FXCollections.observableArrayList();

	Cliente cliente = null;

	@FXML
	void aceptar() {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/airlin/view/SeleccionarBilletes.fxml"));

			Scene scene = new Scene(loader.load());

			SeleccionarBilletesController controller = loader.getController();
			controller.initCliente(cliente);

			stage = new Stage();
			stage.setTitle("Airlin");

			stage.setResizable(false);

			stage.setScene(scene);
			stage.showAndWait();
		} catch (Exception e) {
			new Alerta(e.getMessage()).showAndWait();
			Stage stage = (Stage) buttonAceptar.getScene().getWindow();
			stage.close();
		}
	}

	@FXML
	void actualizarTabla() {
		lista.clear();
		try {
			lista.addAll(daoManager.getClienteDAO().getAll());
		} catch (MySQLDAOException e) {
			new Alerta(e.getMessage()).showAndWait();
		}
		tabla.setItems(lista);
	}

	@FXML
	void cancelar() {
		Stage stage = (Stage) buttonCancelar.getScene().getWindow();
		stage.close();
	}

	public void seleccionarTupla() {

		if (!lista.isEmpty()) {
			cliente = tabla.getSelectionModel().getSelectedItem();
			buttonAceptar.setDisable(false);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			daoManager = (DAOManager) new MySQLDAOManager();
		} catch (SQLException e) {
			new Alerta(e.getMessage()).showAndWait();
		}

		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
		colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
		colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

		tabla.setPlaceholder(new Label("No hay clientes aún."));

		tabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		actualizarTabla();
		buttonAceptar.setDisable(true);
	}
}
