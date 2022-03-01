package airlin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/airlin/view/MenuPrincipal.fxml"));

			Scene scene = new Scene(loader.load());
			stage = new Stage();
			stage.setTitle("Airlin");

			stage.setResizable(false);

			stage.setScene(scene);
			stage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}