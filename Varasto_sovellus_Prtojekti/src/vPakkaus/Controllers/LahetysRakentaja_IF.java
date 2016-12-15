package vPakkaus.Controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import vPakkaus.Asiakas;
import vPakkaus.DAO_Objekti;
import vPakkaus.Tuotejoukko;

/**
 *Lähetysrakentaja on luokka, joka pitää yllä saamaansa tietoa LähetysInformationProvidereilta sekä vastaa näiden erikoistuneiden näyttöjen vaihdosta.
 */
public interface LahetysRakentaja_IF extends NayttojenVaihtaja_IF{

  public void setAsiakas(Asiakas data);
  public void setDate(LocalDate date);
  public LocalDate getDate();
  public Asiakas getAsiakas();

  public void setMuutetutTuoterivit(ArrayList<Tuotejoukko> list);
  /**
   * Metodi, joka käynnistää uuden lähetyksen luonnin. Aloittaa lähetyksen päivityksen jos muokataan olemassaolevaa.
   */
  public void tallennaUusiLahetys();
  /**
   * Metodi jossa asetetaan käyttäjän valitsemat lähetykseen kuuluvat tuotteet.
   * @param list
   */
  public void setTuotteet(ObservableList<DAO_Objekti> list);
  public ObservableList<DAO_Objekti> getTuotteet();
  /**
   * Metodi, joka palauttaa true jos muokataan olemassaolevaa lähetystä muuten false.
   * @return boolean
   */
  public boolean modifyingExcisting();
}
