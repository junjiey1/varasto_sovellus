package vPakkaus.Controllers;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import vPakkaus.DAO_Objekti;
import vPakkaus.Varastoliikenne;

public class shipmentModifyController implements Nakyma_IF ,Initializable{

  @FXML
  private TableView<Varastoliikenne> ShipmentTable;
  @FXML
  private TableColumn<Varastoliikenne, String> column1;
  @FXML
  private TableColumn<Varastoliikenne, String> column2;
  @FXML
  private TableColumn<Varastoliikenne, Date> column3;
  @FXML
  private TableColumn<Varastoliikenne, Integer> column4;
  @FXML
  private Button back;
  @FXML
  private Button search;
  @FXML
  private TextField dateField;
  @FXML
  private TextField customerIDField;
  @FXML
  private TextField userIDField;
  private ObservableList<Varastoliikenne> data
  = FXCollections.observableArrayList();
  private NayttojenVaihtaja_IF vaihtaja;
  private MainController_IF mc;

  @Override
  public void setMainController(MainController_IF m) {
    mc = m;
  }

  @Override
  public void paivita(Object data) {

  }

  @Override
  public void resetoi() {
    data.clear();
  }

  @Override
  public void virheIlmoitus(Object viesti) {
    mc.haeAsiakas(Integer.parseInt(customerIDField.getText()));
  }

  @Override
  public void esiValmistelut() {
//    System.out.println("hei");
//    Varastoliikenne vl = new Varastoliikenne(1, new Date(10, 200, 1000), "test222", 2, 1, -1);
//    vl.setAsiakas(mc.haeAsiakas(11));
//    data.add(vl);
//    ShipmentTable.getItems().setAll(data);
  }

  @Override
  public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
    this.vaihtaja = vaihtaja;
    vaihtaja.rekisteröiNakymaKontrolleri(this, "ShipmentModification");
  }

  public void search(){
    data.addAll(mc.haeLahetykset(Integer.parseInt(customerIDField.getText())));
    ShipmentTable.getItems().setAll(data);
  }

  public void modify(){
    vaihtaja.asetaUudeksiNaytoksi("Test1", "Modifying shipment " + ShipmentTable.getSelectionModel().getSelectedItem().getVarastoliikenneID(),
        ShipmentTable.getSelectionModel().getSelectedItem());
  }

  public void delete(){
  }

  public void backTo(){
    vaihtaja.asetaUudeksiNaytoksi("mainpage", "ManagementMainMenu",3);
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    column1.setCellValueFactory(new PropertyValueFactory<Varastoliikenne, String>("asiakkaanNimi"));
    column2.setCellValueFactory(new PropertyValueFactory<Varastoliikenne, String>("osoite"));
    column3.setCellValueFactory(new PropertyValueFactory<Varastoliikenne, Date>("pvm"));
    column4.setCellValueFactory(new PropertyValueFactory<Varastoliikenne, Integer>("varastoliikenneID"));
  }



}
