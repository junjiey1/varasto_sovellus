package vPakkaus;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainLaunch extends Application {

	private static Stage primaryStage, newStage;
	private static FXMLLoader loader;
	private static AnchorPane APLayout;

	@Override
	public void start(Stage primaStage) throws IOException {
		primaryStage = primaStage;
		primaryStage.setTitle("LOGIN");
		windowConstructor("view/LoginView.fxml", "LOG IN");
	}

//	private void showMainView() throws IOException {
//		loader = new FXMLLoader();
//		loader.setLocation(MainLaunch.class.getResource("view/LoginView.fxml"));
//		APLayout = loader.load();
//		primaryStage.setScene(new Scene(APLayout));
//		primaryStage.show();
//	}
	
	

	public static void windowConstructor(String resource, String title) throws IOException{
		loader = new FXMLLoader();
		loader.setLocation(MainLaunch.class.getResource(resource));
		APLayout = loader.load();
		newStage = new Stage();
		newStage.setTitle(title);
		newStage.setScene(new Scene(APLayout));
		newStage.show();
	}
	
	public static void windowDestroyer(){
		newStage.close();
	}
	
	
	
	
	

	public static void main(String[] args) {
		launch(args);
	}
}
