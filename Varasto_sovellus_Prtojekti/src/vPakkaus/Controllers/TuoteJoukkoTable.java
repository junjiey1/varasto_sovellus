package vPakkaus.Controllers;

import javafx.scene.control.TableView;
import vPakkaus.DAO_Objekti;

public class TuoteJoukkoTable implements Taulukko_IF{

  private TableView<DAO_Objekti> taulukko;

  public TuoteJoukkoTable(TableView<DAO_Objekti> tuoteTaulukko){
    taulukko = tuoteTaulukko;
  }

  @Override
  public void addTableView(TableView table) {

  }

  @Override
  public TableView<DAO_Objekti> getTaulukko() {
    return taulukko;
  }

  @Override
  public boolean paivitaTietokantaan(MainController_IF mc, Nakyma_IF nakyma) {
    return false;
  }

  @Override
  public DAO_Objekti getObject(int index) {
    return taulukko.getItems().get(index);
  }

}
