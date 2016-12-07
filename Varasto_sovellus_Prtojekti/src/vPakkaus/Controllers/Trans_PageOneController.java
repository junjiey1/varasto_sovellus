package vPakkaus.Controllers;

import java.beans.EventHandler;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
//import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import vPakkaus.Asiakas;
import vPakkaus.DAO_Objekti;

public class Trans_PageOneController implements LahetysInformationProvider_IF{
  private MainController_IF mc;
  private NayttojenVaihtaja_IF vaihtaja;
  @FXML
  private DatePicker date;
  @FXML
  private TextField namefield;
  @FXML
  private Button next;
  @FXML
  private Button selectBtn;
  @FXML
  private Label datelabel;
  @FXML
  private Label namelabel;
  @FXML
  private TableView<DAO_Objekti> asiakasTaulukko;
  private Taulukko_IF taulukko;
  private LahetysRakentaja_IF rakentaja;
  private TaulukkoFactory_IF tehdas;
  private boolean selected;
  private Asiakas selectedAsiakas;

  public Trans_PageOneController(){
    tehdas = TaulukkoFactory.getInstance();
    mc=null;
    vaihtaja=null;
    selectedAsiakas=null;
  }


  @Override
  public void setMainController(MainController_IF m) {
    mc = m;
  }

  @Override
  public void paivita(Object data) {
    resetoiTaulukko();
    if(luoUusiTaulukko((ArrayList<DAO_Objekti>)data)){
      täytäTaulukko();
    }else
      virheIlmoitus("Asiakasta annetulla nimellä ei löydy!");
  }

  public void etsiAsiakasta(){
    mc.haeAsiakkaat(namefield.getText());
  }

  private boolean luoUusiTaulukko(ArrayList<DAO_Objekti> p){
    if(p == null || p.size()<=0)
      return false;
    asiakasTaulukko.getColumns().clear();
    taulukko = tehdas.annaTaulukko(p.get(0), p);
    asiakasTaulukko.getColumns().addAll(taulukko.getTaulukko().getColumns());
    return true;
  }

  private void täytäTaulukko() {
    asiakasTaulukko.getItems().addAll(taulukko.getTaulukko().getItems());
    asiakasTaulukko.refresh();
  }

  private void resetoiTaulukko(){
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
  public void resetoi() {
    resetoiTaulukko();
    date.getEditor().clear(); //tyhjentä valittu päivä
    namelabel.setText("");
    datelabel.setText("");
    namefield.setText("");
    rakentaja.setAsiakas(null);
    rakentaja.setDate(null);
  }

  public void valittuAsiakas(){
    selected=false;
    if(asiakasTaulukko.getSelectionModel().getSelectedItem()!=null){
      Asiakas a = (Asiakas)asiakasTaulukko.getSelectionModel().getSelectedItem();
      selectedAsiakas = a;
      namelabel.setText(a.getNimi() + " " + a.getOsoit());
      selected=true;
    }else{
      selectedAsiakas = null;
      virheIlmoitus("Et ole valinnut tarkasteltavaa asiakasta taulukosta");
      selected=false;
    }

    formatDate();

  }

  private void formatDate(){
    if (date.getValue() != null) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
      datelabel.setText(formatter.format(date.getValue()));
    } else {
      datelabel.setText("");
    }
  }

  @Override
  public void virheIlmoitus(Object viesti) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setContentText(viesti.toString());
    alert.showAndWait();
  }



  @Override
  public void esiValmistelut() {
    if(rakentaja.getDate()!=null && rakentaja.getAsiakas()!=null){
      date.setValue(rakentaja.getDate());
      formatDate();
      namelabel.setText(rakentaja.getAsiakas().getNimi());
      namefield.setText(rakentaja.getAsiakas().getNimi());
      selectedAsiakas = rakentaja.getAsiakas();
    }

  }

  @Override
  public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
    this.vaihtaja = vaihtaja;
    vaihtaja.rekisteröiNakymaKontrolleri(this, "Transmission");
  }

  public void back_to() {
    resetoi();
    if(rakentaja.modifyingExcisting()){
      vaihtaja.asetaUudeksiNaytoksi("ShipmentModification", "SHIPMENT Modification", null);
      return;
    }
    vaihtaja.asetaUudeksiNaytoksi("mainpage", "ManagementMainMenu",3);
  }

  public void next_confirm() {
    if(datelabel.getText().equals("") || selectedAsiakas==null){
      virheIlmoitus("Et ole valinnut asiakasta tai päivämäärä!\nTarkista sivun vasemmasta alanurkasta näkyykö asiakas ja päivämäärä.");
    }else{
      rakentaja.setAsiakas(selectedAsiakas);
      rakentaja.setDate(date.getValue());
      vaihtaja.asetaUudeksiNaytoksi("Trans_SelectProduct", null, null);
    }
  }

  @Override
  public void setLahetyksenRakentaja(LahetysRakentaja_IF rakentaja) {
    this.rakentaja = rakentaja;
  }

}
