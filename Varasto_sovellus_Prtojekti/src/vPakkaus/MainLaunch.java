package vPakkaus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import vPakkaus.Controllers.MainController;
import vPakkaus.Controllers.Nakyma_IF;
import vPakkaus.Controllers.NayttojenVaihtaja_IF;
import vPakkaus.Controllers.SetMainController;

/**
 * Paaohjelma alku eli taman luokka aloittaa ohjelma suoritukset. Sisaltaa myos
 * nakymarakentajaa.
 *
 */

public class MainLaunch extends Application implements NayttojenVaihtaja_IF{

	private static Stage MainStage;
	private static FXMLLoader loader;
	private static AnchorPane APLayout;
	private static MainController mc;


	private ViewFactory_IF tehdas;
	private HashMap<String, AnchorPane> anchorMap;
	private HashMap<String, Scene> sceneMap;
	private HashMap<String, Nakyma_IF> luodutNakymaKontrollerit;

	/**
	 * Paaohjelma alku. Sisaankirjautumis nakyma
	 */
	@Override
	public void start(Stage primaStage) throws IOException {
		anchorMap = new HashMap<String, AnchorPane>(); //Tänne tallenetaan fxml tiedoista luodut Anchorpanet
		sceneMap = new HashMap<String, Scene>(); //Tänne tallennetaan jokainen Scene-olio
		luodutNakymaKontrollerit = new HashMap<String, Nakyma_IF>(); //Tänne jokainen FXML näkymä-luokan kontrolleri instanssi
		mc = new MainController();
		tehdas = new AnchorPaneFactory(mc);
		lataaNakymat();
		MainStage = primaStage;
		MainStage.setTitle("test");
		MainStage.setScene(sceneMap.get("login"));
		MainStage.show();
		//windowConstructor("view/LoginView.fxml", "LOG IN", null);
	}

	private void lataaNakymat(){ //Ladataan Anchorpane luokat tehtaan kautta
		System.out.println("Ladataan nakymia");
		anchorMap.put("login",tehdas.annaNakyma("view/LoginView.fxml", this));
		anchorMap.put("mainpage",tehdas.annaNakyma("view/MainPageView.fxml", this));
		anchorMap.put("addpage",tehdas.annaNakyma("view/addProduct.fxml", this));
		anchorMap.put("searchpage",tehdas.annaNakyma("view/SearchProduct.fxml", this));
		sceneMap.put("login", new Scene(anchorMap.get("login")));
		sceneMap.put("mainpage", new Scene(anchorMap.get("mainpage")));
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

	/**
	 * Tuhoa koko nakyma
	 */
	public static void windowDestroyer() {
		MainStage.close();
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

	//http://stackoverflow.com/questions/22161586/javafx-embed-scene-in-scene
	//http://stackoverflow.com/questions/31890137/java-fxml-loading-a-view-for-later-use
	//http://stackoverflow.com/questions/22328087/scene-loads-too-slow
	@Override
	public void asetaUudeksiNaytoksi(String nimi, String otsikko) {
		Scene scene = sceneMap.get(nimi);
		if(scene == null)
			System.out.println("Nakymaa nimella " + nimi + " ei ole ladattu!!!");
		else{
			if(luodutNakymaKontrollerit.containsKey(nimi))
				luodutNakymaKontrollerit.get(nimi).esiValmistelut();
			MainStage.setScene(scene);
			MainStage.setTitle(otsikko);
			MainStage.show();
		}
	}

	@Override
	public AnchorPane getAnchorPane(String nimi) {
		AnchorPane anchor = anchorMap.get(nimi);
		if(anchor == null){
			System.out.println("Nakymaa nimella " + nimi + " ei ole ladattu!!!");
			return null;
		}
		if(luodutNakymaKontrollerit.containsKey(nimi))
			luodutNakymaKontrollerit.get(nimi).esiValmistelut();
		return anchor;
	}

	@Override
	public void rekisteröiNakymaKontrolleri(Nakyma_IF viewController, String nimi) {
		luodutNakymaKontrollerit.put(nimi, viewController);
	}
}
