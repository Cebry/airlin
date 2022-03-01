package airlin.controller;

import javafx.scene.control.Alert;

public class Alerta extends Alert {

	public Alerta(String msg) {
		super(AlertType.ERROR);
		setTitle("Error");
		setHeaderText(msg);
	}
}
