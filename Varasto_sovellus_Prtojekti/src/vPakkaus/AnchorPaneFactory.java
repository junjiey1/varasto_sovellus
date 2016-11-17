package vPakkaus;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import vPakkaus.Controllers.MainController_IF;
import vPakkaus.Controllers.Nakyma_IF;
import vPakkaus.Controllers.NayttojenVaihtaja_IF;

/**
 *
 * @author Julius
 *ViewFactoryn konkreettinen luokka. Rakentaa annetusta fxml-tiedoston nimestä Anchorpaneja
 */
public class AnchorPaneFactory implements ViewFactory_IF{
	private MainController_IF PaaKontrolleri;

	public AnchorPaneFactory(MainController_IF PaaKontrolleri){
		this.PaaKontrolleri = PaaKontrolleri;
	}

	@Override
	public AnchorPane annaNakyma(String fxmlTiedostoNimi, NayttojenVaihtaja_IF vaihtaja) {
		AnchorPane Layout;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxmlTiedostoNimi));
		try {
			Layout = loader.load();
			Nakyma_IF nakyma = loader.getController() instanceof Nakyma_IF ?
				(Nakyma_IF)loader.getController() : null;
			if(nakyma==null){
				System.out.println("ON NULL");
				return null;
			}
			nakyma.setMainController(PaaKontrolleri);
			nakyma.setNaytonVaihtaja(vaihtaja);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return Layout;
	}
}
