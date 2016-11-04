package vPakkaus.Controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import vPakkaus.MainLaunch;
import vPakkaus.ViewFactory_IF;

public class MainPageController implements SetMainController{


	@FXML
	private Tab addProductTab;
	@FXML
	private Tab tab3;
	@FXML
	private Tab tab4;
	@FXML
	private Tab tab5;
	@FXML
	private Label currentUserLbl;

	private Stage newStage;
	//private FXMLLoader loader;
	//private AnchorPane APLayout;
	private ViewFactory_IF NayttoTehdas;
	private MainController_IF mc;
	private Tab activeTab;
	private String resource;

	public MainPageController(){
	}


	public void tabChoose() throws IOException {

		System.gc(); // CLEAR MEMORY

		if (addProductTab.isSelected()) {
			activeTab = addProductTab;
			resource = "view/addProduct.fxml";
		}
		if (tab3.isSelected()) {
			activeTab = tab3;
			resource = "view/SearchProduct.fxml";
		}
		if (tab4.isSelected()) {
			activeTab = tab4;
			resource = "view/addProduct.fxml";
		}
		if (tab5.isSelected()) {
			activeTab = tab5;
			resource = "view/addProduct.fxml";
		}
		MainLaunch.windowConstructor(resource, "VarastoSovellus 1.01", activeTab);
	}

	public void logOut() throws IOException {
		mc.LogOut(); // Poistaa tallennetut käyttäjän nimen ja ID:n
		MainLaunch.windowDestroyer();
		MainLaunch.windowConstructor("view/LoginView.fxml", "LOG IN", null);
	}

	public void whManagement(){

	}



	@Override
	public void setMainController(MainController_IF m) {
		// TODO Auto-generated method stub
		mc = m;
		currentUserLbl.setText("Current user : " + m.getName());
	}
}
