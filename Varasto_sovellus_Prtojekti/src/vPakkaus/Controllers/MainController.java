package vPakkaus.Controllers;

import java.util.ArrayList;
import vPakkaus.DB_AccessObject;
import vPakkaus.Product;

/**
 * Ohjelman pääkontrolleri. Vastaa tiedon välityksestä Näytön ja mallin välillä
 *
 */
public class MainController {
	private DB_AccessObject db;
	private int UserID;
	private String username;

	/**
	 *Tämän luokan konstruktori
	 *Kostruktori luo uuden malli olion ja liittää sen tämän luokan atribuuttiin
	 */
	public MainController() {
		System.out.println("Constructing Main Controller");
		db = new DB_AccessObject();
		username = "undefined";
		UserID = -1;
	}

	/**
	 * Sisaankirjautuminen
	 * Pyytää mallia hakemaan käyttäjä tiedot annetulla käyttäjä nimellä. Palauttaa edelleen mallilta saadun boolean arvon
	 * true jos identifointi onnistui muuten false.
	 * @param username kayttajatunnus
	 * @param password Salasana
	 * @return Palauta booleana, onko sisaankirjautuminen onnistunut.
	 */
	public boolean LogIn(String username, String password) {
		boolean res = false;
		int[] tulos = db.LogIn(username, password);
		if (tulos[0] == 1) {
			res = true;
			UserID = tulos[1];
			this.username = username;
		}
		return res;
	}

	/**
	 * Tavaran lisaaminen
	 *
	 * @param nimi Tavaran nimi
	 * @param paino Tavaran paino
	 * @param tilavuus Tavaran tilavuus
	 * @param hyllypaikka Tavaran hyllypaikka
	 * @param hinta Tavaran hinta
	 * @param maara Tavaran maara
	 * @return Palauta booleana, onko lisaaminen onnistunut.
	 */
	public boolean AddProduct(String nimi, double paino, double tilavuus, String hyllypaikka, float hinta, int maara) {
		boolean res = db.Lisaa(nimi, paino, tilavuus, hyllypaikka, hinta, maara);
		return res;
	}

	/**
	 * Hakee tuotteita nimen perusteella tietokannasta
	 *
	 * @param nimi Tavaran nimi
	 * @return Palauta nulli, jos tavara ei loydy. Muuten palauta Arraylistan, joka sisältää tuote-olioita.
	 */
	public ArrayList<Product> haeTuote(String nimi) {
		ArrayList<Product> res = null;
		res = db.findProducts(nimi);
		for (Product p : res) {
			System.out.println(p.getProduct_name() + p.getID());
		}
		if (res == null)
			return null;
		// product-olio
		return res;
	}

	/**
	 * Paivita tavarat
	 *
	 * @param ArrayList<Product>
	 *
	 * @return palauttaa boolean arvon jos päivitys onnistui
	 */
	public boolean paivitaTuotteet(ArrayList<Product> products){
		boolean res = db.updateProducts(products);
		return res;
	}

	/**
	 * Tallenna muutokset
	 * @param lista Tavaran lista
	 * @return onko tellenus onnistunut.
	 */
	public boolean tallennaMuutokset(ArrayList<Product> lista) {
		boolean res = false;
		return res;
	}
	/**
	 * Poista tavara
	 *
	 * @param nimi tavaran nimi
	 *
	 * @return Paluta booleana.
	 */
	public boolean DeleteProduct(String nimi) {
		boolean res = false;
		return res;
	}

	/**
	 * Hakea kayttajatunnus
	 *
	 * @return Palauta kayttajatunnus
	 */
	public String getName() {
		return username;
	}
	/**
	 * Hakee Kayttaja ID
	 *
	 * @return Paluta kayttaja ID
	 */

	public int getID() {
		return UserID;
	}
	/**
	 * Uloskirjautuminen eli poistetaan edellisen kayttajan tiedot.
	 *
	 */

	public void LogOut() {
		System.out.println("logged out. Deleting saved user information...");
		UserID = -1;
		username = "undefined";
	}
}
