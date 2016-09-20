package vPakkaus;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import vPakkaus.Controllers.LoginController;
import vPakkaus.Controllers.MainController;
import vPakkaus.Controllers.MainPageController;
import vPakkaus.Controllers.ViewController;
import vPakkaus.Controllers.addProductController;

public class MainLaunch extends Application {

	private static Stage newStage;
	private static FXMLLoader loader;
	private static AnchorPane APLayout;
	private static MainController mc;

	@Override
	public void start(Stage primaStage) throws IOException {
		mc = new MainController();
		windowConstructor("view/LoginView.fxml", "LOG IN", null);
	}


	public static void windowConstructor(String resource, String title, Tab activeTab) throws IOException{
		System.out.println(resource);
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
		if(loader.getController() instanceof LoginController)
		{
			LoginController c = (LoginController)loader.getController();
			c.setMainController(mc);
		}
		else if(loader.getController() instanceof MainPageController)
		{
			MainPageController c = (MainPageController)loader.getController();
			c.setMainController(mc);
		}
		else if(loader.getController() instanceof addProductController)
		{
			addProductController c = (addProductController)loader.getController();
			c.setMainController(mc);
		}
	}

	public static void windowDestroyer(){
		newStage.close();
	}

	public static void main(String[] args) throws IOException {
		System.out.println("main");
		launch(args);
	}
}
