package vPakkaus.Controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import vPakkaus.Asiakas;
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
  private MainController_IF mc;
  private NayttojenVaihtaja_IF vaihtaja;
  private boolean allSet;

  //Muuttujat joita käytetään lähetyksessä
  private Asiakas customer;
  private LocalDate date;
  private ArrayList<Tuotejoukko> muuttuneetTuoterivit;
  private ObservableList<DAO_Objekti> valitutTuotteet;

  public Trans_MainController(){
    mc=null;
    vaihtaja=null;
    allSet=false;
  }

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
    if(!allSet){
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
      allSet=true;
    }
    trans_tabPane.getSelectionModel().select(0);
    page_1.setDisable(false);
    selectProduct.setDisable(true);
    confirm.setDisable(true);
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
      trans_tabPane.getSelectionModel().select(0);
      selectProduct.setDisable(true);
    }else if(nimi.equals("Trans_SelectProduct")){
      mc.asetaAktiiviseksiNaytoksi(tab_2Controller);
      selectProduct.setDisable(false);
      trans_tabPane.getSelectionModel().select(1);
      page_1.setDisable(true);
      confirm.setDisable(true);
    }else if(nimi.equals("confirm_tab")){
      mc.asetaAktiiviseksiNaytoksi(tab_3Controller);
      confirm.setDisable(false);
      trans_tabPane.getSelectionModel().select(2);
      selectProduct.setDisable(true);
    }else{
      tab_1Controller.resetoi();
      tab_2Controller.resetoi();
      tab_3Controller.resetoi();
      vaihtaja.asetaUudeksiNaytoksi(nimi, otsikko, preData);
    }
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
  public void setAsiakas(Asiakas data) {
    customer = data;
  }

  @Override
  public void setDate(LocalDate date) {
    this.date = date;
  }

  @Override
  public LocalDate getDate() {
    return date;
  }

  @Override
  public Asiakas getAsiakas() {
    return customer;
  }

  @Override
  public void setMuutetutTuoterivit(ArrayList<Tuotejoukko> list) {
    muuttuneetTuoterivit = list;
  }

  @Override
  public void tallennaUusiLahetys() {
    ArrayList<Tuotejoukko> tjklist = new ArrayList<Tuotejoukko>();
    for(DAO_Objekti dao : valitutTuotteet){
      tjklist.add((Tuotejoukko) dao);
    }
    for(Tuotejoukko tjk : muuttuneetTuoterivit){
      mc.paivitaTuoteRivi(tjk);
    }
    tab_2Controller.resetoi();
    tab_3Controller.resetoi();
    mc.luoUusiLahetys(date, customer.getOsoit(), customer.getID(), tjklist);
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
