module JavaFxExample {

	requires transitive javafx.graphics;
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.base;

	opens airlin to javafx.fxml;
	opens airlin.controller to javafx.fxml;
	opens airlin.model to javafx.fxml;

	exports airlin;
	exports airlin.controller;
	exports airlin.model;
}
