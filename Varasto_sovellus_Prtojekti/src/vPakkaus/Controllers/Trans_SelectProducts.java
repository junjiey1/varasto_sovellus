package vPakkaus.Controllers;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import vPakkaus.DAO_Objekti;
import vPakkaus.Hyllypaikka;
import vPakkaus.Product;
import vPakkaus.Tuotejoukko;

public class Trans_SelectProducts implements Nakyma_IF{

  @FXML
  private TableView<DAO_Objekti> tuoteTaulukko;
  @FXML
  private TableView<DAO_Objekti> lahetysTuotteet;
  @FXML
  private Button hae;
  @FXML
  private TextField tuoteNimi;

  private Taulukko_IF taulukko;
  private MainController_IF mc;
  private TaulukkoFactory tehdas;

  public Trans_SelectProducts(){
    tehdas = new TaulukkoFactory();
  }


  public void reset() {
    int length = tuoteTaulukko.getItems().size(); // Hae taulun rivien määrä
    if (length > 0) {// Jos on rivejä
      for (; 0 < length;) {// Poistetaan yksi kerrallaan
        System.out.println("Deleting");
        tuoteTaulukko.getItems().remove(0);
        length--;
      }
    }
    tuoteTaulukko.refresh(); // Varmuuden vuoksi päivitetään TableView
  }

  private void täytäTaulukko() {
    tuoteTaulukko.getItems().addAll(taulukko.getTaulukko().getItems());
    tuoteTaulukko.refresh();
  }

  private boolean luoUusiTaulukko(ArrayList<DAO_Objekti> p){
    if(p == null || p.size()<=0)
      return false;
    tuoteTaulukko.getColumns().clear();
    taulukko = tehdas.annaTaulukko(p.get(0), p);
    tuoteTaulukko.getColumns().addAll(taulukko.getTaulukko().getColumns());
    return true;
  }

  public void haeTuotteRyhmia(){
    ArrayList<Tuotejoukko> p = new ArrayList<Tuotejoukko>();
    p.add(new Tuotejoukko(new Product("lol", 1.2,1.2,1.2,3.3,1f), new Hyllypaikka("A-4", 9.9, 9.9, 9.9, 10, 2000), 10));
    Object o = (Object)p;
    if(luoUusiTaulukko((ArrayList<DAO_Objekti>)o)){
      täytäTaulukko();
    }
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
