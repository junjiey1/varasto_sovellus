package vPakkaus.Controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import vPakkaus.Asiakas;
import vPakkaus.DB_AccessObject;
import vPakkaus.Hyllypaikka;
import vPakkaus.Product;
import vPakkaus.Tuotejoukko;
import vPakkaus.Varastoliikenne;

/**
 * Ohjelman pääkontrolleri. Vastaa tiedon välityksestä Näytön ja mallin välillä
 *
 */
public class MainController implements MainController_IF{
	private DB_AccessObject db;
	private Nakyma_IF naytto;
	private int userID;
	private String username;

	/**
	 *Tämän luokan konstruktori
	 *Kostruktori luo uuden malli olion ja liittää sen tämän luokan atribuuttiin
	 */
	public MainController() {
		System.out.println("Constructing Main Controller");
		db = new DB_AccessObject();
		username = "undefined";
		userID = -1;
	}

	/**
	 * Tarkistaa jos malliin on asetettu virhe viesti. Jos virhe viesti on asetettu tämä metodi välittää
	 * virhe viestin sisällön aktiiviselle näytölle. Jos virhettä ei ole asetettu tämä funktio ei tee mitään
	 */
	private void checkForErrorMessage(){
	  if (db.getErrorMsg() != null){ //jos error viesti != null on tapahtunut virhe tietokanta operaatiossa
      String syy = db.getErrorMsg(); //Hae virheviestin sisältö
      db.setErrorMsg(null);//Nollaa virheviesti muuttuja mallissa
      naytto.virheIlmoitus(syy);//Välitä virheilmoitus aktiivisellenäytölle
    }
	}

	/**
	 * Sisaankirjautuminen
	 * Pyytää mallia hakemaan käyttäjä tiedot annetulla käyttäjä nimellä. Palauttaa edelleen mallilta saadun boolean arvon
	 * true jos identifointi onnistui muuten false.
	 * @param username kayttajatunnus
	 * @param password Salasana
	 * @return Palauta booleana, onko sisaankirjautuminen onnistunut.
	 */
	public boolean logIn(String username, String password) {
		boolean res = false;
		int[] tulos = db.logIn(username, password);
		if (tulos[0] == 1) {
			res = true;
			userID = tulos[1];
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
	public boolean addProduct(Tuotejoukko joukko) {
		System.out.println(joukko.getProduct().toString());
		boolean res = db.lisaa(joukko);
		if(!res)
		  checkForErrorMessage();
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
		checkForErrorMessage();
		if(res.isEmpty())
		  naytto.virheIlmoitus("Tuotteita sanalla : " + nimi + " ei löydy");
		return res;
	}

	public ArrayList<Hyllypaikka> haeHyllypaikka(String nimi){
		ArrayList<Hyllypaikka> res = null;
		res = new ArrayList<Hyllypaikka>();
		res.add(db.haeHylly(nimi));
		checkForErrorMessage();
		return res;
	}

	public boolean paivitaHylly(Hyllypaikka h){
	  boolean allGood = db.paivitaHylly(h);
	  checkForErrorMessage();
	  System.out.println(allGood);
	  return allGood;
	}

	/**
	 * Paivita tavarat
	 *
	 * @param ArrayList<Product>
	 *
	 * @return palauttaa boolean arvon jos päivitys onnistui
	 */
	public boolean paivitaTuotteet(ArrayList<Product> products){

		boolean res = db.NewProductInformationValidation(products);
		checkForErrorMessage();

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
	public boolean deleteProduct(String nimi) {
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
		return userID;
	}
	/**
	 * Uloskirjautuminen eli poistetaan edellisen kayttajan tiedot.
	 *
	 */

	public void logOut() {
		System.out.println("logged  out. Deleting saved user information...");
		userID = -1;
		username = "undefined";
	}

	@Override
	public void asetaAktiiviseksiNaytoksi(Nakyma_IF naytto) {
		this.naytto = naytto;
		//System.out.println(naytto.toString());
		naytto.esiValmistelut();
	}

	@Override
  public Asiakas haeAsiakas(Object criteria) {
	  Asiakas res=null;
	  if(criteria instanceof Integer){
	    res = db.haeAsiakas(((Integer) criteria).intValue());
	  }else if(criteria instanceof String){
	    res = db.haeAsiakas(criteria.toString());
	  }else{
	    System.out.println("Criteria not supported");
	  }
	  checkForErrorMessage();
	  if(res==null)
	    return new Asiakas("", "", "", "", "", "");
	  return res;
  }

	@Override
	public void haeAsiakkaat(String nimi) {
		ArrayList<Asiakas> lista = db.haeAsiakkaat(nimi);
		checkForErrorMessage();
		naytto.paivita(lista);
	}

	@Override
	public void tallennaAsiakas(Asiakas asiakas) {
		if(db.addAsiakas(asiakas)){
			naytto.paivita("Asiakas nimellä " + asiakas.getNimi() + " lisättiin onnistuneesti");
			naytto.paivita(db.haeAsiakas(asiakas.getNimi()));
		}else{
		  checkForErrorMessage();
			naytto.virheIlmoitus("Asiakas nimellä " + asiakas.getNimi() + " lisääminen epäonnistui");
		}
	}

	@Override
	public void updateAsiakas(Asiakas a){
		System.out.println(a.getNimi() + " " + a.getPosnumero() + " " + a.getNumero() + " id: " + a.getID());
		if(db.updateAsiakas(a))
			naytto.paivita("Päivitys onnistui!");
		else
			naytto.virheIlmoitus("Päivitys epäonnistui!");
		checkForErrorMessage();
	}

  @Override
  public void haeTuotejoukot(String nimi) {
    Product p = db.findProduct(nimi);
    checkForErrorMessage();
    if(p==null)
      return;
    ArrayList<Tuotejoukko> res = db.haeTuotteenKaikkiTuoterivit(p);
    checkForErrorMessage();
    naytto.paivita(res);
  }

  @Override
  public boolean luoUusiLahetys(LocalDate pvm, String osoite, int asiakasID, ArrayList<Tuotejoukko> tjklist) {
    Varastoliikenne vl = new Varastoliikenne(1, Date.valueOf(pvm), osoite, userID, asiakasID,-1);
    boolean allGood = db.luoVarastoliikenne(vl, tjklist);
    checkForErrorMessage();
    if(allGood)
      naytto.paivita("Uusi lähetys lisättiin onnistuneesti!");
    return allGood;
  }

  @Override
  public void paivitaTuoteRivi(Tuotejoukko tjk) {
    db.muokkaaTuoteriviä(tjk);
    checkForErrorMessage();
    int uusiMaara = db.tuotteidenMaaraHyllyssa(tjk.getTuotteenNimi(), tjk.getHyllynNimi());
    if(uusiMaara==0){
      db.deleteTuoterivi(tjk);
      System.out.println("Poistetaan tuoterivi");
    }
    checkForErrorMessage();
  }

  @Override
  public List<Varastoliikenne> haeLahetykset(int id) {
    List res = db.haeVarastoliikenteenRivit(id);
    return res;
  }

  @Override
  public void deleteLahetys(int id) {
    boolean res = db.deleteLahetys(id);
    checkForErrorMessage();
    if(!res)
      naytto.virheIlmoitus("Lähetyksen poisto epäonnistui");
    else
      naytto.paivita("Poisto onnistui!");
  }

  @Override
  public void haeTuotejoukkoHyllysta(String hyllynTunnus, String tuotteenNimi) {
    Tuotejoukko tj = db.haeTuotejoukkoHyllysta(hyllynTunnus, tuotteenNimi);
    checkForErrorMessage();
    if(tj==null){
      naytto.virheIlmoitus("Tuotetta " + tuotteenNimi + " ei ole hyllyssä " + hyllynTunnus);
      return;
    }
    naytto.paivita(tj);
  }

  @Override
  public boolean paivitaLahetys(Varastoliikenne vl) {
    if(!db.tallennaMuokattuLahetys(vl, userID)){
      checkForErrorMessage();
      naytto.paivita("EI voitu paivittaa lähetystä");
      return false;
    }
    naytto.paivita("Päivitys onnistui!");
    return true;
  }

  @Override
  public TreeMap<Date, Integer> haeTietoja(Date d1, Date d2, String nimi) {
    Product p = db.findProduct(nimi);
    checkForErrorMessage();
    if(p==null){
      return null;
    }
    TreeMap<Date, Integer> res = db.haeTietoja(d1, d2, p.getID());
    checkForErrorMessage();
    if(res.isEmpty())
      System.out.println("tyhjä");
    return res;
  }
}
