package vPakkaus.Controllers;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import vPakkaus.DAO_Objekti;
import vPakkaus.Tuotejoukko;

public class Trans_MainController implements Nakyma_IF, LahetysRakentaja_IF{

  @FXML
  protected Tab selectProduct;
  @FXML
  protected Tab confirm;
  @FXML
  protected Tab page_1;
  @FXML
  protected TabPane trans_tabPane;
  @FXML
  private LahetysInformationProvider_IF tab_1Controller;
  @FXML
  private LahetysInformationProvider_IF tab_2Controller;
  @FXML
  private LahetysInformationProvider_IF tab_3Controller;
  protected Tab activeTab;
  private MainController_IF mc;
  private NayttojenVaihtaja_IF vaihtaja;

  //Muuttujat joita käytetään lähetyksessä
  private String customer;
  private String date;
  private ArrayList<Tuotejoukko> MuuttuneetTuoterivit;
  private ObservableList<DAO_Objekti> valitutTuotteet;

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

  }

  @Override
  public void resetoi() {

  }

  @Override
  public void virheIlmoitus(Object viesti) {

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
    tab_1Controller.setLahetyksenRakentaja(this);
    tab_2Controller.setLahetyksenRakentaja(this);
    tab_3Controller.setLahetyksenRakentaja(this);
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
      tab_3Controller.paivita(preData);
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
    return null;
  }

  @Override
  public void setAsiakasnimi(String data) {
    customer = data;
  }

  @Override
  public void setDate(String date) {
    this.date = date;
  }

  @Override
  public String getDate() {
    return date;
  }

  @Override
  public String getAsiakasnimi() {
    return customer;
  }

  @Override
  public void setMuutetutTuoterivit(ArrayList<Tuotejoukko> list) {
    MuuttuneetTuoterivit = list;
  }

  @Override
  public void tallennaMuutetutTuoterivit() {
    //mc->malli->tietokanta
  }

  @Override
  public void setTuotteet(ObservableList<DAO_Objekti> list) {
    valitutTuotteet = list;
  }

  @Override
  public ObservableList<DAO_Objekti> getTuotteet() {
    return valitutTuotteet;
  }

}
