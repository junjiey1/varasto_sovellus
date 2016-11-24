package vPakkaus.Controllers;

public class KuvaajaController implements Nakyma_IF{


  private MainController_IF mc;
  private NayttojenVaihtaja_IF vaihtaja;
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
  public void back(){//Button Callback funktio
    vaihtaja.asetaUudeksiNaytoksi("ManagementMainMenu", "ManagementMainMenu",null);

  }

  @Override
  public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
    this.vaihtaja = vaihtaja;
    vaihtaja.rekisteröiNakymaKontrolleri(this, "Trans_SelectProduct");

  }

}
