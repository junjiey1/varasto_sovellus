package vPakkaus;

import java.io.IOException;
import java.sql.Date;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainLaunch extends Application {

	private static Stage primaryStage, newStage;
	private static FXMLLoader loader;
	private static AnchorPane APLayout;
	private static DB_AccessObject db;
//nimi, paino, tilavuus, hyllypaikka, saapumisp�iv�, l�ht�p�iv�, hinta(can be null)
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
		db = new DB_AccessObject();
		//DB_AccessObject.Lisaa("testi", 0.5, 1.5, "A-08", new Date(2016-1900, 2, 2), new Date(2, 1, 1995), 3.4f, 1, 1, 2);
		launch(args);
	}
}
