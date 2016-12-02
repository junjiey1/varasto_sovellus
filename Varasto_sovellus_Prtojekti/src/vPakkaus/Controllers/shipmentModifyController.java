package vPakkaus.Controllers;

import java.util.TreeMap;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class shipmentModifyController implements Nakyma_IF{

  @FXML
  private TableView<?> ShipmentTable;
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
    ShipmentTable.getItems().addAll();
  }

  @Override
  public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
    this.vaihtaja = vaihtaja;
    vaihtaja.rekisteröiNakymaKontrolleri(this, "Trans_SelectProduct");
  }

  public void modify(){

  }

  public void delete(){
    //TreeMap map = new TreeMap();
    //map.put(key, value)
  }

  public void back(){
    vaihtaja.asetaUudeksiNaytoksi("mainpage", "ManagementMainMenu",3);
  }



}
