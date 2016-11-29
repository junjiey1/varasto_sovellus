package vPakkaus.Controllers;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class Trans_MainController implements Nakyma_IF, NayttojenVaihtaja_IF{

  @FXML
  protected Tab selectProduct;
  @FXML
  protected Tab confirm;
  @FXML
  protected Tab page_1;
  @FXML
  protected TabPane trans_tabPane;
  @FXML
  private Trans_PageOneController tab_1Controller;
  @FXML
  private Trans_SelectProducts tab_2Controller;
  @FXML
  private Trans_confirmController tab_3Controller;
  protected Tab activeTab;
  private MainController_IF mc;
  private NayttojenVaihtaja_IF vaihtaja;

  public void iniatilize(){

  }

  public void back_to(){

  }
  public void next_confirm(){

  }

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
    trans_tabPane.getSelectionModel().select(0);
    selectProduct.setDisable(true);
    confirm.setDisable(true);
    System.out.println("Asetetaan....");
    tab_1Controller.setMainController(mc);
    tab_2Controller.setMainController(mc);
    tab_3Controller.setMainController(mc);
    vaihtaja.rekisteröiNakymaKontrolleri(tab_1Controller, "Transmission");
    vaihtaja.rekisteröiNakymaKontrolleri(tab_2Controller, "Trans_SelectProduct");
    vaihtaja.rekisteröiNakymaKontrolleri(tab_3Controller, "lol");
    tab_1Controller.setNaytonVaihtaja(this);
    tab_2Controller.setNaytonVaihtaja(this);
    tab_3Controller.setNaytonVaihtaja(this);
    mc.asetaAktiiviseksiNaytoksi(tab_1Controller);
  }

  @Override
  public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
    this.vaihtaja = vaihtaja;
    vaihtaja.rekisteröiNakymaKontrolleri(this, "Test1");
  }

  @Override
  public void asetaUudeksiNaytoksi(String nimi, String otsikko, Object preData) {
    if(nimi.equals("Transmission")){
      mc.asetaAktiiviseksiNaytoksi(tab_1Controller);
      page_1.setDisable(false);
      activeTab = page_1;
      trans_tabPane.getSelectionModel().select(0);
      selectProduct.setDisable(true);
    }else if(nimi.equals("Trans_SelectProduct")){
      mc.asetaAktiiviseksiNaytoksi(tab_2Controller);
      selectProduct.setDisable(false);
      activeTab = selectProduct;
      trans_tabPane.getSelectionModel().select(1);
      page_1.setDisable(true);
    }else if(nimi.equals("confirm_tab")){
      mc.asetaAktiiviseksiNaytoksi(tab_3Controller);
      confirm.setDisable(false);
      activeTab = confirm;
      trans_tabPane.getSelectionModel().select(2);
      selectProduct.setDisable(true);
    }else
      vaihtaja.asetaUudeksiNaytoksi(nimi, otsikko, preData);
  }

  @Override
  public AnchorPane getAnchorPane(String nimi) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void rekisteröiNakymaKontrolleri(Nakyma_IF viewController, String avain) {
    // TODO Auto-generated method stub
  }

  @Override
  public Nakyma_IF haeKontrolleri(String nimi) {
    // TODO Auto-generated method stub
    return null;
  }

}
