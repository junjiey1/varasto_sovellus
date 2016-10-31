package vPakkaus;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import vPakkaus.Controllers.MainController;
import vPakkaus.Controllers.SetMainController;

/**
 * Paaohjelma alku eli taman luokka aloittaa ohjelma suoritukset. Sisaltaa myos
 * nakymarakentajaa.
 *
 */

public class MainLaunch extends Application {

	private static Stage newStage;
	private static FXMLLoader loader;
	private static AnchorPane APLayout;
	private static MainController mc;

	/**
	 * Paaohjelma alku. Sisaankirjautumis nakyma
	 */
	@Override
	public void start(Stage primaStage) throws IOException {

		mc = new MainController();

		mc = new MainController();
		windowConstructor("view/LoginView.fxml", "LOG IN", null);
	}

	/**
	 * Medodi, joka vastaa uusien nakymien luonnista.
	 *
	 * @param resource
	 *            resursoidaan
	 * @param title
	 *            Tabin title.
	 * @param activeTab
	 *            Painike olio
	 * @throws IOException
	 *             Heittaa error,jos jotain on epaonnistunut.
	 */
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

	/**
	 * Tuhoa koko nakyma
	 */
	public static void windowDestroyer() {
		newStage.close();
	}

	/**
	 * Kaynnistaa ohjema
	 *
	 * @param args
	 *            sisältää toimitettu komentorivin argumentteja
	 * @throws IOException
	 *             Jos kaynnistaminen epaonnistuu.
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("main");
		launch(args);
	}
}
