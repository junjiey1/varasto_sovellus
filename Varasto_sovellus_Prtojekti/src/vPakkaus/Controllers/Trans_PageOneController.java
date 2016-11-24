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

public class Trans_PageOneController implements Nakyma_IF{
  private MainController_IF mc;
  private NayttojenVaihtaja_IF vaihtaja;
  @FXML
  private DatePicker date;
  @FXML
  private TextField namefield;
  @FXML
  private Tab selectProduct;
  @FXML
  private Tab confirm;
  @FXML
  private Tab page_1;
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
  @FXML
  private TabPane trans_tabPane;
  private Taulukko_IF taulukko;
  private TaulukkoFactory tehdas;
  private Tab activeTab;
  private boolean selected;


  public Trans_PageOneController(){
    tehdas = new TaulukkoFactory();
    mc=null;
    vaihtaja=null;
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
    date.getEditor().clear(); //tyhjentä valittu päivä
    namelabel.setText(null);
    datelabel.setText(null);

  }

  public void valittuAsiakas(){
    selected=false;
    if(asiakasTaulukko.getSelectionModel().getSelectedItem()!=null){
      Asiakas a = (Asiakas)asiakasTaulukko.getSelectionModel().getSelectedItem();
      namelabel.setText(a.getNimi());
      selected=true;
    }else
      virheIlmoitus("Et ole valinnut tarkasteltavaa asiakasta taulukosta");
      selected=false;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    if (date.getValue() != null) {
      //String dateFormat = date.get;
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
    // TODO Auto-generated method stub

  }


  public void back(){//Button Callback funktio
    vaihtaja.asetaUudeksiNaytoksi("ManagementMainMenu", "ManagementMainMenu",null);


    resetoi();

  }

  public void next(){
    if(date.getValue()==null || asiakasTaulukko.getSelectionModel().getSelectedItem()==null && selected==false){
      virheIlmoitus("Kenttä ei voi olla tyhjä");


    }else{

      selectProduct.setDisable(false);
      activeTab = selectProduct;
      activeTab.setContent(vaihtaja.getAnchorPane("Trans_SelectProduct"));
      trans_tabPane.getSelectionModel().select(1);
      page_1.setDisable(true);
    }



  }


  @Override
  public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {

    this.vaihtaja = vaihtaja;
    vaihtaja.rekisteröiNakymaKontrolleri(this, "Transmission");
  }

}
