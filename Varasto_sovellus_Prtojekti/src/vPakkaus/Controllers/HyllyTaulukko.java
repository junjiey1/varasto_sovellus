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
  public TableView<DAO_Objekti> getTaulukko() {
    return hyllyTaulu;
  }

  private boolean isEmpty() {
    for (Hyllypaikka p : paivitettavat) {
      if (p != null)
        return false;
    }
    return true;
  }

  @Override
  public boolean paivitaTietokantaan(MainController_IF mc, Nakyma_IF nakyma) {
    if (paivitettavat == null || isEmpty()) // Hylly lista on tyhjä käyttäjä ei oo muokannut tuotteita
      return false;
    for (Hyllypaikka h : paivitettavat) {
      if(!mc.paivitaHylly(h)){ //Jos tapahtui virhe hyllyn tietojen päivittämisessä
        return false; //palautetaan false
      }
    }
    return true; //päästään tänne jos kaikki ok
  }

}
