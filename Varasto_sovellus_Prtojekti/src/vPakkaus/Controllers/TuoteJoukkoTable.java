package vPakkaus.Controllers;

import javafx.scene.control.TableView;
import vPakkaus.DAO_Objekti;

public class TuoteJoukkoTable implements Taulukko_IF{

  private TableView<DAO_Objekti> taulukkoTuoteRyhmille;
  //private TableView<DAO_Objekti> taulukkoLahetyksenTuotteille;

  public TuoteJoukkoTable(TableView<DAO_Objekti> tuoteTaulukko){
    taulukkoTuoteRyhmille = tuoteTaulukko;
  }

  @Override
  public TableView<DAO_Objekti> getTaulukko() {
    return taulukkoTuoteRyhmille;
  }

  @Override
  public boolean paivitaTietokantaan(MainController_IF mc, Nakyma_IF nakyma) {
    return false;
  }

}
