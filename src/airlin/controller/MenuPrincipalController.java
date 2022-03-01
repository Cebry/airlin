package airlin.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import airlin.model.Billete;
import airlin.model.Cliente;
import airlin.model.Factura;
import airlin.model.dao.DAOManager;
import airlin.model.dao.mysql.MySQLDAOManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuPrincipalController {

	@FXML
	private Button buttonBilletes;

	@FXML
	private Button buttonClientes;

	@FXML
	private Button buttonFacturas;

	@FXML
	private Button buttonReiniciar;

	@FXML
	void mostrarMenuClientes() {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/airlin/view/TablaClientes.fxml"));

		Scene scene;
		try {
			scene = new Scene(loader.load());
			Stage stage = new Stage();
			stage.setTitle("Airlin - Clientes");

			stage.setResizable(false);

			stage.setScene(scene);

			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (IOException e) {
			new Alerta(e.getMessage()).showAndWait();
		}
	}

	@FXML
	void mostrarMenuBilletes() {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/airlin/view/TablaBilletes.fxml"));

		Scene scene;
		try {
			scene = new Scene(loader.load());
			Stage stage = new Stage();
			stage.setTitle("Airlin - Billetes");

			stage.setResizable(false);

			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (IOException e) {
			new Alerta(e.getMessage()).showAndWait();
		}
	}

	@FXML
	void mostrarMenuFacturas() {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/airlin/view/TablaFacturas.fxml"));

		Scene scene;
		try {
			scene = new Scene(loader.load());
			Stage stage = new Stage();
			stage.setTitle("Airlin - Facturas");

			stage.setResizable(false);

			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (IOException e) {
			new Alerta(e.getMessage()).showAndWait();
		}
	}

	@FXML
	void reiniciarDatos() {

		try {

			DAOManager daoManager = new MySQLDAOManager();

			ArrayList<Cliente> clientesIniciales = new ArrayList<Cliente>(daoManager.getClienteDAO().getAll());
			ArrayList<Factura> facturasIniciales = new ArrayList<Factura>(daoManager.getFacturaDAO().getAll());
			ArrayList<Billete> billetesIniciales = new ArrayList<Billete>(daoManager.getBilleteDAO().getAll());

			for (Billete i : billetesIniciales) {
				daoManager.getBilleteDAO().delete(i);
			}

			for (Factura i : facturasIniciales) {
				daoManager.getFacturaDAO().delete(i);
			}

			for (Cliente i : clientesIniciales) {
				daoManager.getClienteDAO().delete(i);
			}

			clientesIniciales.clear();
			facturasIniciales.clear();
			billetesIniciales.clear();

			clientesIniciales.add(new Cliente("John", "Doe", "31025655N", "785241965"));
			clientesIniciales.add(new Cliente("Barbara", "Foo", "17478777L", "658247582"));
			clientesIniciales.add(new Cliente("Jim", "Douglas", "79149554E", "685745825"));
			clientesIniciales.add(new Cliente("Tom", "Donn", "59342054F", "685248521"));

			for (Cliente i : clientesIniciales) {
				daoManager.getClienteDAO().insert(i);
			}

			facturasIniciales.add(new Factura(LocalDate.now().minusWeeks(3), clientesIniciales.get(0).getId()));
			facturasIniciales.add(new Factura(LocalDate.now().minusWeeks(2), clientesIniciales.get(1).getId()));
			facturasIniciales.add(new Factura(LocalDate.now().minusWeeks(1), clientesIniciales.get(2).getId()));
			facturasIniciales.add(new Factura(LocalDate.now().minusDays(5), clientesIniciales.get(3).getId()));

			for (Factura i : facturasIniciales) {
				daoManager.getFacturaDAO().insert(i);
			}

			billetesIniciales.add(new Billete((long) 1, (float) 22.4, LocalDate.now().minusDays(3), null));
			billetesIniciales.add(new Billete((long) 2, (float) 22.4, LocalDate.now().minusDays(3),
					facturasIniciales.get(0).getId()));
			billetesIniciales.add(new Billete((long) 3, (float) 22.4, LocalDate.now().minusDays(3),
					facturasIniciales.get(0).getId()));
			billetesIniciales.add(new Billete((long) 4, (float) 22.4, LocalDate.now().minusDays(3), null));
			billetesIniciales.add(new Billete((long) 5, (float) 22.4, LocalDate.now().minusDays(3), null));
			billetesIniciales.add(new Billete((long) 6, (float) 22.4, LocalDate.now().minusDays(3),
					facturasIniciales.get(1).getId()));
			billetesIniciales.add(new Billete((long) 7, (float) 22.4, LocalDate.now().minusDays(3), null));
			billetesIniciales.add(new Billete((long) 8, (float) 22.4, LocalDate.now().minusDays(3),
					facturasIniciales.get(3).getId()));
			billetesIniciales.add(new Billete((long) 9, (float) 22.4, LocalDate.now().minusDays(3), null));
			billetesIniciales.add(new Billete((long) 10, (float) 22.4, LocalDate.now().minusDays(3),
					facturasIniciales.get(2).getId()));
			billetesIniciales.add(new Billete((long) 1, (float) 22.4, LocalDate.now().plusDays(3), null));
			billetesIniciales.add(new Billete((long) 2, (float) 22.4, LocalDate.now().plusDays(3), null));
			billetesIniciales.add(new Billete((long) 3, (float) 22.4, LocalDate.now().plusDays(3), null));
			billetesIniciales.add(new Billete((long) 4, (float) 22.4, LocalDate.now().plusDays(3), null));
			billetesIniciales.add(new Billete((long) 5, (float) 22.4, LocalDate.now().plusDays(3), null));
			billetesIniciales.add(new Billete((long) 6, (float) 22.4, LocalDate.now().plusDays(3), null));
			billetesIniciales.add(new Billete((long) 7, (float) 22.4, LocalDate.now().plusDays(3), null));
			billetesIniciales.add(new Billete((long) 8, (float) 22.4, LocalDate.now().plusDays(3), null));
			billetesIniciales.add(new Billete((long) 9, (float) 22.4, LocalDate.now().plusDays(3), null));
			billetesIniciales.add(new Billete((long) 10, (float) 22.4, LocalDate.now().plusDays(3), null));

			for (Billete i : billetesIniciales) {
				daoManager.getBilleteDAO().insert(i);
			}
			new Alert(AlertType.INFORMATION, "Datos reiniciados.").showAndWait();

		} catch (Exception e) {
			new Alerta(e.getMessage()).showAndWait();
		}
	}
}