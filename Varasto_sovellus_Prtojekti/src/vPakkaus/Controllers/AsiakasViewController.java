package vPakkaus.Controllers;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import vPakkaus.Asiakas;
import vPakkaus.DAO_Objekti;

public class AsiakasViewController implements Nakyma_IF{

	@FXML
	private TableView<DAO_Objekti> asiakasTaulukko;
	@FXML
	private TextField searchCustomer;
	private Taulukko_IF taulukko;
	private TaulukkoFactory_IF tehdas;
	private MainController_IF mc;
	private NayttojenVaihtaja_IF v;


	public AsiakasViewController(){
		tehdas = TaulukkoFactory.getInstance();
		mc=null;
		v=null;
	}

	/**
	 * ViewCustomer napin callback-funktio. Pyytää näytönvaihtajaa asettamaan käyttöliittymän näkymäksi
	 * asiakkaan tietoja käsittelevän näytön.
	 */
	public void vaihdaAsiakkaanTarkasteluun(){
		if(asiakasTaulukko.getSelectionModel().getSelectedItem()!=null){
			Asiakas a = (Asiakas)asiakasTaulukko.getSelectionModel().getSelectedItem();
			v.asetaUudeksiNaytoksi("customer", "Asiakas : " + a.getNimi() + " ", a);
		}else
			virheIlmoitus("Et ole valinnut tarkasteltavaa asiakasta taulukosta");
	}

	/**
	 * AddCustomer napin callback-funktio. Pyytää näytönvaihtajaa asettamaan käyttöliittymän näkymäksi
	 * asiakkaan luontia käsittelevän näytön.
	 */
	public void vaihdaUudenAsiakkaanLuontiin(){
		v.asetaUudeksiNaytoksi("customer", "Asiakas : UUSI ASIAKAS", null);
	}

	/**
	 * Back napin callback-funktio. Pyytää näytönvaihtajaa asettamaan käyttöliittymän näkymäksi
	 * main menu näytön.
	 */
	public void back(){
		v.asetaUudeksiNaytoksi("ManagementMainMenu", "WareHouseManagement", null);
	}

	@Override
	public void setMainController(MainController_IF m) {
		mc = m;
	}

	@Override
	public void paivita(Object data) {
		resetoi();
		if(luoUusiTaulukko((ArrayList<DAO_Objekti>)data)){
			täytäTaulukko();
		}else
			virheIlmoitus("Asiakasta annetulla nimellä ei löydy!");
	}

	/**
	 * Täyttää käyttöliittymän taulukon asiakkailla.
	 */
	private void täytäTaulukko() {
		asiakasTaulukko.getItems().addAll(taulukko.getTaulukko().getItems());
		asiakasTaulukko.refresh();
	}

	/**
	 * Luo Kolumnit käyttöliittymän taulukolle.
	 */
	private boolean luoUusiTaulukko(ArrayList<DAO_Objekti> p){
		if(p == null || p.size()<=0)
			return false;
		asiakasTaulukko.getColumns().clear();
		taulukko = tehdas.annaTaulukko(p.get(0), p);
		asiakasTaulukko.getColumns().addAll(taulukko.getTaulukko().getColumns());
		return true;
	}

	/**
	 * Pyytää PääKontrolleria hakemaan asiakkaita.
	 */
	public void etsiAsiakasta(){
		mc.haeAsiakkaat(searchCustomer.getText());
	}

	@Override
	public void resetoi() {
		int length = asiakasTaulukko.getItems().size(); // Hae taulun rivien määrä
		if (length > 0) {// Jos on rivejä
			for (; 0 < length;) {// Poistetaan yksi kerrallaan
				asiakasTaulukko.getItems().remove(0);
				length--;
			}
		}
		asiakasTaulukko.refresh(); // Varmuuden vuoksi päivitetään TableView
	}

	@Override
	public void virheIlmoitus(Object viesti) {
		JOptionPane.showMessageDialog(null, viesti.toString(), null, JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void esiValmistelut() {

	}

	@Override
	public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
		v = vaihtaja;
		v.rekisteröiNakymaKontrolleri(this, "customerview");
	}

}
