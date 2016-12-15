package vPakkaus.Controllers;

/**
 *
 * Tämä luokka on lähetyksen teon tiettyyn osaan erikoistunut näyttö-olio, joka välittää Lähetyksen rakentajalle käyttäjän syötteistä saatua tietoa
 */
public interface LahetysInformationProvider_IF extends Nakyma_IF{
  /**
   * Liittää Lähetysrakentajan tämän luokan muuttujiin
   * @param rakentaja
   */
  public void setLahetyksenRakentaja(LahetysRakentaja_IF rakentaja);
}
