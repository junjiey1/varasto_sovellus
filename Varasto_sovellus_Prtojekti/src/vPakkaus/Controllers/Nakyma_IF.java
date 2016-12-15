package vPakkaus.Controllers;

/**
 *
 * @author Julius
 *Näkymä rajapinta. Kertoo mitä funktioita käyttöliittymien kontrollien pitää toteuttaa.
 *Kaikki ohjelman fxml näkymä kontrollit toteuttaa tämän rajapinnan.
 */

public interface Nakyma_IF extends SetMainController{
	/**
	 * @param data
	 */
	public void paivita (Object data);
	/**
	 *Resetoi näkymän kontrolli sen alkutilaan.
	 */
	public void resetoi();
	/**
	 *Näyttää virheilmoituksen käyttäjälle ja virheilmoituksen sisältö määräytyy viestin arvosta.
	 */
	public void virheIlmoitus(Object viesti);
	/**
	 *Juuri ennenkuin tämä näkymä tulee käyttäjälle näkyviin voidaan ko. näkymän kontrolleria pyytää tekemään
	 *esivalmisteluita esim. arvojen alustus, tietojen haku yms.
	 */
	public void esiValmistelut();
	/**
	 * Asettaa tälle näkymä kontrollerille oman vaihtajansa.
	 */
	public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja);
}
