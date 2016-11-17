package vPakkaus.Controllers;

public class WareHouseManagement_MainMenu_Controller implements Nakyma_IF{

	private MainController_IF mc;
	private NayttojenVaihtaja_IF vaihtaja;

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
	public void esiValmistelut() {
		// TODO Auto-generated method stub

	}

	public void aktivoiAsiakasIkkuna(){
		vaihtaja.asetaUudeksiNaytoksi("customerview", "Asiakkaat", null);
	}

	public void aktivoiMainPageIkkuna(){
		vaihtaja.asetaUudeksiNaytoksi("mainpage", "VarastoSovellus", null);
	}

	@Override
	public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
		this.vaihtaja = vaihtaja;
		vaihtaja.rekisteröiNakymaKontrolleri(this, "ManagementMainMenu");
	}

}
