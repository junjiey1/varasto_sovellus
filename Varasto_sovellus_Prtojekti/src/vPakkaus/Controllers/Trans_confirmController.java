package vPakkaus.Controllers;

public class Trans_confirmController implements Nakyma_IF{

  private NayttojenVaihtaja_IF vaihtaja;
  private MainController_IF mc;

  @Override
  public void setMainController(MainController_IF m) {
    mc = m;
  }

  @Override
  public void paivita(Object data) {
    // TODO Auto-generated method stub

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
    this.vaihtaja = vaihtaja;
    vaihtaja.rekisteröiNakymaKontrolleri(this, "confirm_tab");
  }

  public void back_to() {
    vaihtaja.asetaUudeksiNaytoksi("Trans_SelectProduct", null,null);
  }

}
