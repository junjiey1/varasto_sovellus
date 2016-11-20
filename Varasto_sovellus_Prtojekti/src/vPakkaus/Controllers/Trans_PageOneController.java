package vPakkaus.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import vPakkaus.DAO_Objekti;

public class Trans_PageOneController implements Nakyma_IF{
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

  public void aktivoiTransmission(){
    vaihtaja.asetaUudeksiNaytoksi("Transmission", "Lahetys", null);
  }

  public void back(){//Button Callback funktio
    vaihtaja.asetaUudeksiNaytoksi("ManagementMainMenu", "ManagementMainMenu",null);
  }


  @Override
  public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
    this.vaihtaja = vaihtaja;
    vaihtaja.rekisteröiNakymaKontrolleri(this, "Transmission");
  }

}
