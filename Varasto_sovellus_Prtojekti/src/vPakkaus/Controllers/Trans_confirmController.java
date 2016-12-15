package vPakkaus.Controllers;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import vPakkaus.DAO_Objekti;
import vPakkaus.LanguageUtil;

public class Trans_confirmController implements LahetysInformationProvider_IF{

  @FXML
  private TableView<DAO_Objekti> confirmTable;
  @FXML
  private Label dateLabel;
  @FXML
  private Label customerNameLabel;
  @FXML
  private Label osoiteLabel;
  @FXML
  private Button confirmButton;
  private NayttojenVaihtaja_IF vaihtaja;
  private MainController_IF mc;
  private LahetysRakentaja_IF rakentaja;
  private TaulukkoFactory_IF tehdas;

  public Trans_confirmController(){
   tehdas = TaulukkoFactory.getInstance();
  }

  public void initialize(){
    confirmTable.getColumns().addAll(tehdas.buildHelperTable(null).getColumns());
  }

  public void finalConfirmation(){
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("CONFIRM");
    if(rakentaja.modifyingExcisting())
      alert.setHeaderText("Olet tallentamassa muokattua lähetystä tietokantaan\njatketaanko?");
    else
      alert.setHeaderText("Olet tallentamassa uutta lähetystä tietokantaan\njatketaanko?");
    alert.setContentText("Jatketaanko?");
    ButtonType buttonTypeOne = new ButtonType("Kyllä");
    ButtonType buttonTypeTwo = new ButtonType("Ei", ButtonData.CANCEL_CLOSE);
    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == buttonTypeOne){
      rakentaja.tallennaUusiLahetys();
    }
  }

  private void resetTables(TableView<DAO_Objekti> taulukko) { //
    int length = taulukko.getItems().size(); // Hae taulun rivien määrä
    if (length > 0) {// Jos on rivejä
      for (; 0 < length;) {// Poistetaan yksi kerrallaan
        System.out.println("Deleting");
        taulukko.getItems().remove(0);
        length--;
      }
    }
    taulukko.refresh(); // Varmuuden vuoksi päivitetään TableView
  }

  @Override
  public void setMainController(MainController_IF m) {
    mc = m;
  }

  @Override
  public void paivita(Object data) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Ilmoitus");
    alert.setContentText(data.toString());
    alert.showAndWait();
  }

  @Override
  public void resetoi() {
    resetTables(confirmTable);
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
    resetTables(confirmTable);
    confirmTable.getItems().addAll(rakentaja.getTuotteet());
    confirmTable.refresh();
    customerNameLabel.setText("Customer name : " + rakentaja.getAsiakas().getNimi());
    osoiteLabel.setText("Address information : " + rakentaja.getAsiakas().getKaupun() + " " + rakentaja.getAsiakas().getOsoit() + " " + rakentaja.getAsiakas().getPosnumero());
    dateLabel.setText("Date for shipment : " + rakentaja.getDate());
  }

  @Override
  public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
    this.vaihtaja = vaihtaja;
    vaihtaja.rekisteröiNakymaKontrolleri(this, "confirm_tab");
  }

  public void cancel(){
    if(rakentaja.modifyingExcisting()){
      vaihtaja.asetaUudeksiNaytoksi("ShipmentModification", "SHIPMENT Modification", null);
      return;
    }
    vaihtaja.asetaUudeksiNaytoksi("mainpage", LanguageUtil.getMessageFromResource("program_name"),3);
  }

  public void back_to() {
    vaihtaja.asetaUudeksiNaytoksi("Trans_SelectProduct", null,null);
  }

  @Override
  public void setLahetyksenRakentaja(LahetysRakentaja_IF rakentaja) {
    this.rakentaja = rakentaja;
  }

}
