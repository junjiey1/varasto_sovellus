package vPakkaus.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

  private MainController_IF mc;


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

  @Override
  public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
    vaihtaja.rekisteröiNakymaKontrolleri(this, "Trans_SelectProduct");
  }

}
