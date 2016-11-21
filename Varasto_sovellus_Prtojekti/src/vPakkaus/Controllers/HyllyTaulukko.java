package vPakkaus.Controllers;

import javafx.scene.control.TableView;
import vPakkaus.DAO_Objekti;
import vPakkaus.Hyllypaikka;
import vPakkaus.Product;

public class HyllyTaulukko implements Taulukko_IF{

  private Hyllypaikka[] paivitettavat;
  private TableView<DAO_Objekti> hyllyTaulu;

  public HyllyTaulukko(TableView<DAO_Objekti> hyllyTaulukko, Hyllypaikka[] lista){
    hyllyTaulu = hyllyTaulukko;
    paivitettavat = lista;
    int lask = 0;
    for(Hyllypaikka h : lista){
      if(h!=null){
        hyllyTaulu.getItems().add(h);
        lista[lask] = null;
        lask++;
      }
    }
  }

  @Override
  public void addTableView(TableView table) {
    // TODO Auto-generated method stub

  }

  @Override
  public TableView<DAO_Objekti> getTaulukko() {
    return hyllyTaulu;
  }

  @Override
  public boolean paivitaTietokantaan(MainController_IF mc, Nakyma_IF nakyma) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public DAO_Objekti getObject(int index) {
    // TODO Auto-generated method stub
    return null;
  }

}
