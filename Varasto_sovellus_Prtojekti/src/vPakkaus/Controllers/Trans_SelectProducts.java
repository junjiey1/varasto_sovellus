package vPakkaus.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import vPakkaus.DAO_Objekti;

public class Trans_SelectProducts implements Nakyma_IF{

  @FXML
  private TableView<DAO_Objekti> tuoteTaulukko;
  @FXML
  private TableView<DAO_Objekti> lahetysTuotteet;
  @FXML
  private Button hae;
  @FXML
  private TextField tuoteNimi;
  @FXML
  private Tab selectProduct;
  @FXML
  private Tab confirm;
  @FXML
  private Tab page_1;
  @FXML
  private TabPane trans_tabPane;

  private MainController_IF mc;
  private NayttojenVaihtaja_IF vaihtaja;
  private Tab activeTab;


  public void haeTuotteRyhmia(){

  }

  @Override
  public void setMainController(MainController_IF m) {
    mc=m;
  }

  @Override
  public void paivita(Object data) {

  }

  @Override
  public void resetoi() {

  }

  @Override
  public void virheIlmoitus(Object viesti) {
    // TODO Auto-generated method stub

  }

  @Override
  public void esiValmistelut() {
    // TODO Auto-generated method stub

  }

  public void back(){
    page_1.setDisable(false);

    activeTab = page_1;
    activeTab.setContent(vaihtaja.getAnchorPane("Transmission"));
    trans_tabPane.getSelectionModel().select(0);
    selectProduct.setDisable(true);
  }
  public void next(){

  }


  @Override
  public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
    vaihtaja.rekisteröiNakymaKontrolleri(this, "Trans_SelectProduct");
  }

}
