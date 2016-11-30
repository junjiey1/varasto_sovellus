package vPakkaus.Controllers;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import vPakkaus.Asiakas;
import vPakkaus.DAO_Objekti;
import vPakkaus.Tuotejoukko;

public interface LahetysRakentaja_IF extends NayttojenVaihtaja_IF{

  public void setAsiakas(Asiakas data);
  public void setDate(String date);
  public String getDate();
  public Asiakas getAsiakas();
  public void setMuutetutTuoterivit(ArrayList<Tuotejoukko> list);
  public void tallennaMuutetutTuoterivit();
  public void setTuotteet(ObservableList<DAO_Objekti> list);
  public ObservableList<DAO_Objekti> getTuotteet();
}
