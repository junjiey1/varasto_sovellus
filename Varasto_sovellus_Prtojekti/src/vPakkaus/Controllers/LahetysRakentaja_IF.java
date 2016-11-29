package vPakkaus.Controllers;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import vPakkaus.DAO_Objekti;
import vPakkaus.Tuotejoukko;

public interface LahetysRakentaja_IF extends NayttojenVaihtaja_IF{

  public void setAsiakasnimi(String data);
  public void setDate(String date);
  public String getDate();
  public String getAsiakasnimi();
  public void setMuutetutTuoterivit(ArrayList<Tuotejoukko> list);
  public void tallennaMuutetutTuoterivit();
  public void setTuotteet(ObservableList<DAO_Objekti> list);
  public ObservableList<DAO_Objekti> getTuotteet();
}
