package vPakkaus.Controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import vPakkaus.MainLaunch;

public class MainPageController {

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
	MainController mc;

	Tab activeTab;
	String resource;

	public void initialize() {
	}

	public void setMainController(MainController m) {
		mc = m;
		currentUserLbl.setText("CURRENT USER :  " + mc.getName());
	}

	public void tabChoose() throws IOException {

		System.gc(); // CLEAR MEMORY

		if (addProductTab.isSelected()) {
			activeTab = addProductTab;
			resource = "view/addProduct.fxml";
		}
		if (tab3.isSelected()) {
			activeTab = tab3;
			resource = "view/addProduct.fxml";
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
}
