package vPakkaus;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import vPakkaus.Controllers.MainController_IF;
import vPakkaus.Controllers.Nakyma_IF;

public class ViewFactory implements ViewFactory_IF{

	private FXMLLoader loader;
	private AnchorPane APLayout;
	private MainController_IF PaaKontrolleri;

	public ViewFactory(MainController_IF PaaKontrolleri){
		loader = new FXMLLoader();
		this.PaaKontrolleri = PaaKontrolleri;
	}

	@Override
	public Scene annaNakyma(String fxmlTiedostoNimi) {
		loader.setLocation(MainLaunch.class.getResource(fxmlTiedostoNimi));
		try {
			APLayout = loader.load();
			Nakyma_IF nakyma = loader.getController() instanceof Nakyma_IF ?
				(Nakyma_IF)loader.getController() : null;
			if(nakyma==null){
				return null;
			}
			nakyma.setMainController(PaaKontrolleri);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return new Scene(APLayout);
	}



}
