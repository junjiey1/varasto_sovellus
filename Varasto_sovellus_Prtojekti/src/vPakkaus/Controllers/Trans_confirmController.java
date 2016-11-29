package vPakkaus.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import vPakkaus.DAO_Objekti;

public class Trans_confirmController implements LahetysInformationProvider_IF{

  @FXML
  private TableView<DAO_Objekti> confirmTable;
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
  }

  private void setLabel(String s){

  }

  @Override
  public void resetoi() {

  }

  @Override
  public void virheIlmoitus(Object viesti) {

  }

  @Override
  public void esiValmistelut() {
    resetTables(confirmTable);
    confirmTable.getItems().addAll(rakentaja.getTuotteet());
    confirmTable.refresh();
  }

  @Override
  public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
    this.vaihtaja = vaihtaja;
    vaihtaja.rekisteröiNakymaKontrolleri(this, "confirm_tab");
  }

  public void back_to() {
    vaihtaja.asetaUudeksiNaytoksi("Trans_SelectProduct", null,null);
  }

  @Override
  public void setLahetyksenRakentaja(LahetysRakentaja_IF rakentaja) {
    this.rakentaja = rakentaja;
  }

}
