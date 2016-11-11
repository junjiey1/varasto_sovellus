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

public class MainPageController implements Nakyma_IF{


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
	//private FXMLLoader loader;
	//private AnchorPane APLayout;
	private MainController_IF mc;
	private Tab activeTab;
	private String resource;
	private NayttojenVaihtaja_IF vaihtaja;

	public MainPageController(){
	}


	public void tabChoose() throws IOException {

		if (addProductTab.isSelected()) {
			activeTab = addProductTab;
			activeTab.setContent(vaihtaja.getAnchorPane("addpage"));
		}
		if (tab3.isSelected()) {
			activeTab = tab3;
			activeTab.setContent(vaihtaja.getAnchorPane("searchpage"));
		}
		if (tab4.isSelected()) {
			activeTab = tab4;
			//vaihtaja.asetaUudeksiNaytoksi("addpage", "VarastoSovellus");
			resource = "view/addProduct.fxml";
		}
		if (tab5.isSelected()) {
			activeTab = tab5;
			//vaihtaja.asetaUudeksiNaytoksi("addpage", "VarastoSovellus");
			resource = "view/addProduct.fxml";
		}
		//MainLaunch.windowConstructor(resource, "VarastoSovellus 1.01", activeTab);
	}

	public void logOut() throws IOException {
		mc.LogOut(); // Poistaa tallennetut käyttäjän nimen ja ID:n
		vaihtaja.asetaUudeksiNaytoksi("login", "VarastoSovellus");
	}

	public void whManagement(){
		vaihtaja.asetaUudeksiNaytoksi("customer", "lol");
	}



	@Override
	public void setMainController(MainController_IF m) {
		mc = m;
	}


	@Override
	public void paivita(Object data) {

	}


	@Override
	public void resetoi() {

	}

	@Override
	public void virheIlmoitus(Object viesti) {

	}


	@Override
	public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
		this.vaihtaja = vaihtaja;
		this.vaihtaja.rekisteröiNakymaKontrolleri(this,"mainpage");
	}


	@Override
	public void esiValmistelut() {
		System.out.println(mc.getName());
		currentUserLbl.setText("Current user : " + mc.getName());
	}
}
