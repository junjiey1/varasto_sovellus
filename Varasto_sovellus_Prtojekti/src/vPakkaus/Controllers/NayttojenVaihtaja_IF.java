package vPakkaus.Controllers;

import javafx.scene.layout.AnchorPane;
/**
 * Tämä luokka vastaa aktiivisten näyttöjen vaihdosta ohjelman ajonaikana.
 */
public interface NayttojenVaihtaja_IF{
  /**
   * Asettaa uudeksi näytöksi sen näytön, jonkan nimi vastaa "nimi"-parametria ja asettaa sille otsikoksi minkä se saa otsikon arvona. preData on dataa, joka välitetään näytön ohjaimelle, joka on tulossa aktiiviseksi.
   * @param nimi
   * @param otsikko
   * @param preData
   */
	public void asetaUudeksiNaytoksi(String nimi, String otsikko, Object preData);
	/**
	 * Hakee nimen määrittelemän Anchorpanen. Hakee näkymän jolle ei ole tarvinnut luoda uutta Scenia. Tähän lukeutuu mm. ne näkymät jotka vaihtuvat kun painetaan tabeja painaessa.
	 * @param nimi
	 * @return
	 */
	public AnchorPane getAnchorPane(String nimi);
	public void rekisteröiNakymaKontrolleri(Nakyma_IF viewController, String avain);
	public Nakyma_IF haeKontrolleri(String nimi);
}
