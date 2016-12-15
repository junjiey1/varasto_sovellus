package vPakkaus.Controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import vPakkaus.Asiakas;
import vPakkaus.Hyllypaikka;
import vPakkaus.Product;
import vPakkaus.Tuotejoukko;
import vPakkaus.Varastoliikenne;

/**
 *Ohjelman pääkontrolleri vastaa aktiivisen näytön(Näytön minkä käyttäjä ohjelman suorituksen aikana sillä hetkellä näkee) ja mallin kommunikoinnista.
 *
 */
public interface MainController_IF {
  /**
   *Metodi jota kutsutaan kun halutaan asettaa uusi näyttö aktiiviseksi ja jolle tämä kontrolleri välittää mahdollisia päivitys viestejä
   * */
	public void asetaAktiiviseksiNaytoksi(Nakyma_IF naytto);
	/**
	 *
	 *Metodi jossa välitetään mallille käyttäjätunnukset. Palauttaa true jos käyttäjän autentikointi onnistui muuten false
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public boolean logIn(String username, String password);
	/**
	 *Metodi missä välitetään Tuotejoukko olio mallille. Tuotejoukko koostuu Tuotteen metatiedoista, tuotteen määrästä ja millä hyllyllä nämä tuotteet sijaitsevat.
	 *Kutsutaan kun halutaan lisätä tuotteita tietokantaan
	 * @param joukko
	 * @return boolean
	 */
	public boolean addProduct(Tuotejoukko joukko);
	/**
	 * Välittää mallille Tuotejoukko-olion jota käytetään tuotteiden määrän tai sijainnin päivityksessä tietokannassa
	 * @param tjk
	 */
	public void paivitaTuoteRivi(Tuotejoukko tjk);
	/**
   * Hakee tiettyä tuotetta määritellyltä hyllypaikalta. Välittää tulokset takaisin näytölle
   * @param String Hyllyntunnus
   * @param String tunnus
   */
	public void haeTuotejoukkoHyllysta(String hyllynTunnut, String tuotteenNimi);
	/**
	 *Välittää mallille pyynnön jossa haetaan koko tietokannasta tuotteita joiden nimessä ilmenee sama rakenne kuin tämän metodin parametrina tuleva merkkijono.
	 *Eli esim. jos saadan parametrina "ssa" saadaan tulokseksi esim. "kissa" ja muita tuotteitta joiden nimissä on "ssa".
	 * @param nimi
	 * @return ArrayList<Product>
	 */
	public ArrayList<Product> haeTuote(String nimi);
	/**
	 * Hae käyttäjän id
	 * @return
	 */
	public int getID();
	/**
   * Hae käyttäjän nimi
   * @return
   */
	public String getName();
	/**
	 * Välittää listan mallille jossa malli pyrkii päivittämään kaikki listassa olevien tuotteiden tiedot tietokantaan. Palauttaa true jos operaatio onnistui muuten false.
	 * @param products
	 * @return boolean
	 */
	public boolean paivitaTuotteet(ArrayList<Product> products);
	/**
	 * Hae hyllypaikkaa nimellä.
	 * @param nimi
	 * @return
	 */
	public ArrayList<Hyllypaikka> haeHyllypaikka(String nimi);
	/**
	 * Välittää Hyllypaikka-olion mallille missä se päivitetään tietokantaan. Palauttaa true jos operaatio onnistui muuten false
	 * @param h
	 * @return boolean
	 */
	public boolean paivitaHylly(Hyllypaikka h);
	/**
	 * Hakee asiakasta tietyn kriteerin avulla. Kelvolliset kriteerit joko Integer(asiakasnumero) tai String(asiakkaan nimi).
	 * @param criteria
	 * @return Asiakas
	 */
	public Asiakas haeAsiakas(Object criteria);
	/**
	 * Hakee asiakkaita nimellä. Palauttaa listan aktiiviselle näytölle.
	 * @param nimi
	 */
	public void haeAsiakkaat(String nimi);
	/**
	 * Tallentaa asiakkaan tietokantaan
	 */
	public void tallennaAsiakas(Asiakas asiakas);
	/**
	 * Ulos kirjautuminen
	 */
	public void logOut();
	/**
	 * Pyytää mallia päivittämään asiakkaan tietokantaan
	 * @param a
	 */
	public void updateAsiakas(Asiakas a);
	public void haeTuotejoukot(String nimi);
	/**
	 * Välittää mallille annetut parametrit ja luo uuden lähetyksen.
	 * @param pvm
	 * @param osoite
	 * @param asiakasID
	 * @param tjk
	 * @return
	 */
	public boolean luoUusiLahetys(LocalDate pvm, String osoite, int asiakasID, ArrayList<Tuotejoukko> tjk);
	public boolean paivitaLahetys(Varastoliikenne vl);
	public List<Varastoliikenne> haeLahetykset(int id);
	public void deleteLahetys(int id);
	/**
	 * Pyytää mallilta kuinka monta samaa tuotetta on menny eri päivien aikana. d1(alkupäivä), d2(loppupäivä) ja tuotteen nimi, jonka suhteen katotaan kuinka monta on mennyt.
	 * Palauttaa TreeMap<Date, Integer>-olion missä Date on päivämäärä ja Integer menneet tuotteet.
	 * @param d1
	 * @param d2
	 * @param tuotteenNimi
	 * @return TreeMap
	 */
	public TreeMap<Date, Integer> haeTietoja(Date d1, Date d2, String tuotteenNimi);
}
