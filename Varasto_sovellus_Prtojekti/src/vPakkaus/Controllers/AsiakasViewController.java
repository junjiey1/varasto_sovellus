package vPakkaus.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class AsiakasViewController implements Nakyma_IF{

	@FXML
	private TableView asiakasTaulukko;

	private TaulukkoFactory tehdas;
	private MainController_IF mc;
	private NayttojenVaihtaja_IF v;
	private String nimi;


	public void AsiakasViewController(){
		tehdas = new TaulukkoFactory();
	}

	public void vaihdaAsiakkaanTarkasteluun(){
		//asiakasTaulukko.getSelectionModel().getSelectedItem();
		v.asetaUudeksiNaytoksi("customer", "Asiakas : UUSI ASIAKAS");
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
		// TODO Auto-generated method stub

	}

	@Override
	public void virheIlmoitus(Object viesti) {
		// TODO Auto-generated method stub

	}

	@Override
	public void esiValmistelut() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
		v = vaihtaja;
	}

}
