package vPakkaus.Controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
  private Button next;
  @FXML
  private Label datelabel;
  @FXML
  private Label namelabel;
  @FXML
  private TableView<DAO_Objekti> asiakasTaulukko;
  private Taulukko_IF taulukko;
  private TaulukkoFactory tehdas;
  private Tab activeTab;


  public Trans_PageOneController(){
    tehdas = new TaulukkoFactory();
    mc=null;
    vaihtaja=null;
  }


  @Override
  public void setMainController(MainController_IF m) {
    mc = m;
  }

  public void tabChoose() throws IOException {

    if (selectProduct.isSelected()) {
      activeTab = selectProduct;
      activeTab.setContent(vaihtaja.getAnchorPane("Trans_SelectProduct"));
    }
    if (confirm.isSelected()) {
      activeTab = confirm;
      activeTab.setContent(vaihtaja.getAnchorPane("Trans_confirm"));
    }
  }





//  @FXML
//  private void getDate(ActionEvent event) {
//
//
//    if (date.getValue() != null) {
//        datelabel.setText(date.toString());
//    } else {
//      datelabel.setText("");
//    }
//}
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
  }

  public void valittuAsiakas(){
//    if(asiakasTaulukko.getSelectionModel().getSelectedItem()!=null){
//      namelabel.setText(asiakasTaulukko.getItems().toString());
//    }else
//      virheIlmoitus("Et ole valinnut tarkasteltavaa asiakasta taulukosta");
//
//    if (date.getValue() != null) {
//      String dateFormat = date.toString();
//      datelabel.setText(dateFormat);
//  } else {
//    datelabel.setText("");
//  }
  }

  @Override
  public void virheIlmoitus(Object viesti) {

  }

  @Override
  public void esiValmistelut() {
    // TODO Auto-generated method stub

  }


  public void back(){//Button Callback funktio
    vaihtaja.asetaUudeksiNaytoksi("ManagementMainMenu", "ManagementMainMenu",null);
  }

  public void next(){

    if (next.isPressed()) {
      activeTab = selectProduct;
      activeTab.setContent(vaihtaja.getAnchorPane("Trans_SelectProduct"));
    }
  }


  @Override
  public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
    this.vaihtaja = vaihtaja;
    vaihtaja.rekisteröiNakymaKontrolleri(this, "Transmission");
  }

}
