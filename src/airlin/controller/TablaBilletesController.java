package airlin.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import airlin.model.Billete;
import airlin.model.dao.DAOManager;
import airlin.model.dao.mysql.MySQLDAOManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TablaBilletesController implements Initializable {

	@FXML
	private TableColumn<Billete, Long> colPlaza;

	@FXML
	private TableColumn<Billete, Float> colPrecio;

	@FXML
	private TableColumn<Billete, Long> colId;

	@FXML
	private TableColumn<Billete, Date> colFecha;

	@FXML
	private TableColumn<Billete, Long> colIdFactura;

	@FXML
	private TableView<Billete> tabla;

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
	private DatePicker pickerFecha;

	@FXML
	private TextField textPlaza;

	@FXML
	private TextField textPrecio;

	DAOManager daoManager = null;

	ObservableList<Billete> lista = FXCollections.observableArrayList();

	Billete billete = null;

	TableViewSelectionModel<Billete> listaSeleccionada = null;

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
		colIdFactura.setCellValueFactory(new PropertyValueFactory<>("idFactura"));

		tabla.setPlaceholder(new Label("No hay billetes aún."));

		listaSeleccionada = tabla.getSelectionModel();

		listaSeleccionada.setSelectionMode(SelectionMode.SINGLE);

		actualizarTabla();
		limpiarFormulario();
	}

	public void actualizarTabla() {
		lista.clear();
		try {
			lista.addAll(daoManager.getBilleteDAO().getAll());
		} catch (Exception e) {
			new Alerta(e.getMessage()).showAndWait();
		}
		tabla.setItems(lista);
	}

	public void insertarBillete() {

		try {
			billete = new Billete(Long.parseLong(textPlaza.getText()), Float.parseFloat(textPrecio.getText()),
					pickerFecha.getValue(), null);

			daoManager.getBilleteDAO().insert(billete);
		} catch (Exception e) {
			new Alerta(e.getMessage()).showAndWait();
		}
		actualizarTabla();
		limpiarFormulario();
	}

	public void actualizarBillete() {

		try {
			billete = new Billete(billete.getId(), Long.parseLong(textPlaza.getText()),
					Float.parseFloat(textPrecio.getText()), pickerFecha.getValue(), billete.getIdFactura());
			daoManager.getBilleteDAO().update(billete);
		} catch (Exception e) {
			new Alerta(e.getMessage()).showAndWait();
		}
		actualizarTabla();
		limpiarFormulario();
	}

	public void borrarBillete() {

		try {
			daoManager.getBilleteDAO().delete(billete);
		} catch (Exception e) {
			new Alerta(e.getMessage()).showAndWait();
		}

		actualizarTabla();
		limpiarFormulario();
	}

	public void limpiarFormulario() {
		billete = null;

		textPlaza.setText("");
		textPrecio.setText("");
		pickerFecha.setValue(null);

		buttonInsertar.setDisable(false);
		buttonBorrar.setDisable(true);
		buttonActualizar.setDisable(true);
	}

	public void seleccionarTupla() {

		if (!lista.isEmpty()) {
			billete = tabla.getSelectionModel().getSelectedItem();

			actualizarFormulario();

			buttonInsertar.setDisable(true);
			buttonBorrar.setDisable(false);
			buttonActualizar.setDisable(false);
		}
	}

	public void actualizarFormulario() {
		if (billete != null) {
			textPlaza.setText(billete.getPlaza().toString());
			textPrecio.setText(billete.getPrecio().toString());
			pickerFecha.setValue(billete.getFecha());
		}
	}
}
