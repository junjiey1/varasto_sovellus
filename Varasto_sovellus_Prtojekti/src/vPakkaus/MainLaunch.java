package vPakkaus;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainLaunch extends Application {

	private static Stage newStage;
	private static FXMLLoader loader;
	private static AnchorPane APLayout;
	private static DB_AccessObject db;

	@Override
	public void start(Stage primaStage) throws IOException {
		windowConstructor("view/LoginView.fxml", "LOG IN", null);
	}


	public static void windowConstructor(String resource, String title, Tab activeTab) throws IOException{
		loader = new FXMLLoader();
		loader.setLocation(MainLaunch.class.getResource(resource));
		APLayout = loader.load();

		if (activeTab != null){
			activeTab.setContent(APLayout);
		}else{
			newStage = new Stage();
			newStage.setTitle(title);
			newStage.setScene(new Scene(APLayout));
			newStage.show();
		}
	}

	public static void windowDestroyer(){
		newStage.close();
	}

	public static void main(String[] args) {
		db = new DB_AccessObject();
		//DB_AccessObject.Lisaa("testi", 0.5, 1.5, "A-08", new Date(2016-1900, 2, 2), new Date(2, 1, 1995), 3.4f, 1, 1, 2);
		//addProductController.readFromFile();
		launch(args);
	}
}
