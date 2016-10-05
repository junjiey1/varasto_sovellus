package vPakkaus;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import vPakkaus.Controllers.LoginController;
import vPakkaus.Controllers.MainController;
import vPakkaus.Controllers.MainPageController;
import vPakkaus.Controllers.MuokkaaProductController;
import vPakkaus.Controllers.SetMainController;
import vPakkaus.Controllers.addProductController;

public class MainLaunch extends Application {

	private static Stage newStage;
	private static FXMLLoader loader;
	private static AnchorPane APLayout;
	private static MainController mc;

	@Override
	public void start(Stage primaStage) throws IOException {
		mc = new MainController();
		mc.haeTuote("ju");
//		Product pro1 = new Product("julius", "ab", 1.0, 2.0, 3);
//		Product pro2 = new Product("jeeejeee", "abcd", 1.0, 2.0, 3);
//		pro1.setID(56);
//		pro2.setID(55);
//		ArrayList<Product> prod = new ArrayList<>();
//		prod.add(pro1);
//		prod.add(pro2);
//		mc.paivitaTuotteet(prod);
		windowConstructor("view/LoginView.fxml", "LOG IN", null);
	}

	public static void windowConstructor(String resource, String title, Tab activeTab) throws IOException {
		System.out.println(resource);
		loader = new FXMLLoader();
		loader.setLocation(MainLaunch.class.getResource(resource));
		APLayout = loader.load();

		if (activeTab != null) {
			activeTab.setContent(APLayout);
		} else {
			newStage = new Stage();
			newStage.setTitle(title);
			newStage.setScene(new Scene(APLayout));
			newStage.show();
		}
		if (loader.getController() instanceof SetMainController) {
			SetMainController c = (SetMainController) loader.getController();
			c.setMainController(mc);
		}
	}

	public static void windowDestroyer() {
		newStage.close();
	}

	public static void main(String[] args) throws IOException {
		System.out.println("main");
		launch(args);
	}
}
