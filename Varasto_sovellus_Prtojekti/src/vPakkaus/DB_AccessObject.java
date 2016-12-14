package vPakkaus;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import TietokantaKyselyt.AsiakasDB;
import TietokantaKyselyt.HyllyDB;
import TietokantaKyselyt.ProductDB;
import TietokantaKyselyt.TuoteriviDB;
import TietokantaKyselyt.UsersDB;
import TietokantaKyselyt.VarastoliikenneDB;
import TietokantaKyselyt.VarastoliikenneriviDB;
import TietokantaKyselyt.LampotilaDB;

/**
 * Luokka vastaa tietokantayhteydesta ja kyselyista.
 *
 */
public class DB_AccessObject {
  // ACCESS SQL_DB_OBJ.
  private Connection conn = null;

  private LampotilaDB lampotiladb;
  private String errorMsg;
  private AsiakasDB asiakasdb;
  private UsersDB usersdb;
  private ProductDB productdb;
  private TuoteriviDB tuoterividb;
  private HyllyDB hyllydb;
  private VarastoliikenneDB varastoliikennedb;
  private VarastoliikenneriviDB vrividb;

  /**
   * Luodaan yhteys virtuaalikoneeseen ja tietokantaan.
   *
   */

  public DB_AccessObject() {
    errorMsg = null;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      // conn =
      // DriverManager.getConnection("jdbc:mysql://10.114.32.19:3306/varasto",
      // "jenkins", "jenkins");
      conn = DriverManager.getConnection("jdbc:mysql://localhost:9000/varasto", "toimi", "toimi");
    } catch (SQLException e) {
      System.out.println("Yhteyden muodostaminen epäonnistui");
      try {
        System.out.println("Yritetään muodostaa yhteys Jenkinsillä");
        conn = DriverManager.getConnection("jdbc:mysql://10.114.32.19:3306/varasto", "jenkins",
            "jenkins");
      } catch (SQLException e1) {
        e1.printStackTrace();
      }
    } catch (ClassNotFoundException e) {
      System.out.println("JDBC-ajurin lataus epäonnistui");
    }
    try {
      if (conn != null && conn.isValid(10)) {
        usersdb = new UsersDB(conn);
        hyllydb = new HyllyDB(conn, this);
        lampotiladb = new LampotilaDB(conn);
        asiakasdb = new AsiakasDB(conn,this);
        productdb = new ProductDB(conn, lampotiladb, this);
        tuoterividb = new TuoteriviDB(conn, productdb, hyllydb, this);
        varastoliikennedb = new VarastoliikenneDB(conn, asiakasdb, usersdb, this);
        vrividb = new VarastoliikenneriviDB(conn, productdb, varastoliikennedb, this);
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  // -----METODIT-----//

  /**
   * Kirjautuminen, tarkistaa löytyykö vastaava käyttäjätunnus ja salasana tietokannasta.
   *
   */

  public int[] logIn(String uname, String pword) {

    return usersdb.logIn(uname, pword);
  }

  /**
   * Hae virhe viesti.
   *
   * @return virhe viesti
   */
  public String getErrorMsg() {
    return errorMsg;
  }

  /**
   * Aseta virhe viesti.
   */
  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  /**
   * Lisaa tuotejoukon tietokantaan.
   *
   * @param joukko
   *          Tuotejoukon lisaaminen (Tuotejoukko)
   *
   * @return Onnistuuko tavaran lisaaminen (boolean)
   */

  public boolean lisaa(Tuotejoukko joukko) {

    ArrayList<Boolean> onkoVirheitä = new ArrayList<Boolean>();

    Hyllypaikka hyllypaikka = haeHylly(joukko.getHylly().getNimi());
    // Jos hyllypaikkaa ei ole olemassa
    if (hyllypaikka == null) {
      // Error viestille asetetaan arvo.
      // PääKontrolleri huomaa tämän ja välittää viestin Näytölle
      errorMsg = "Hyllypaikkaa annetulla arvolla " + joukko.getHylly().getNimi() + " ei löytynyt";
      // Poistutaan funktiosta ja palautetaan false
      return false;
    }
    joukko.setHylly(hyllypaikka);

    // Haetaan hyllypaikan tuotteet
    ArrayList<String> Hyllyn_tuotteet = haeHyllypaikanTuotteet(joukko.getHylly().getNimi());

    boolean mahtuuko = mahtuukoTuotteetHyllyyn(joukko);
    boolean onkoLampotilaSopiva = checkLampotila(joukko);

    if (!mahtuuko) {
      System.out.println("Tuotteet eivät mahdu hyllyyn");
      onkoVirheitä.add(false);
      // Error viestille asetetaan arvo.
      // PääKontrolleri huomaa tämän ja välittää viestin Näytölle
      errorMsg = "Tuote " + joukko.getProduct().getProduct_name() + " ei mahdu hyllyyn "
          + joukko.getHylly().getNimi();
      // Jos tuotetta on jo hyllypaikassa
    } else if (!onkoLampotilaSopiva) {
      System.out.println("Tuotteen lämpötila ei ole sopiva kyseiseen hyllypaikkaan");
      onkoVirheitä.add(false);
      // Error viestille asetetaan arvo.
      // PääKontrolleri huomaa tämän ja välittää virheviestin Näytölle
      errorMsg = "Tuotteen lämpötila ei ole sopiva kyseiseen hyllypaikkaan";
    } else if (Hyllyn_tuotteet.contains(joukko.getProduct().getProduct_name())) {
      System.out.println("Tuotetta on jo hyllyssä");

      // haetaan jo hyllyssä olevien tuotteiden määrä
      int maarahyllyssa = tuotteidenMaaraHyllyssa(joukko.getProduct().getProduct_name(),
          joukko.getHylly().getNimi());
      // lisätään uusi määrä vanhaan määrään
      int uusimaara = joukko.getMaara() + maarahyllyssa;
      joukko.setMaara(uusimaara);
      muokkaaTuoteriviä(joukko);

      System.out.println("Tuotteiden määrää kasvatettu");

    } else {

      Product product = findProduct(joukko.getProduct().getProduct_name());
      // Jos kyseistä tuotetta ei ole vielä olemassa ollenkaan
      if (product == null) {
        if (!addProduct(joukko.getProduct())) // Jos tuotteen lisäyksessä tapahtui virhe
          return false;
        joukko.getProduct().setID(findProduct(joukko.getProduct().getProduct_name()).getID());
        if (joukko.getProduct().getMax_temperature() != null
            && joukko.getProduct().getMin_temperature() != null)
          addTemperatures(joukko.getProduct());
      } else {
        product = findProduct(joukko.getProduct().getProduct_name());
        // joukolle täytyy asettaa tuote ID, joka haetaan
        joukko.getProduct().setID(product.getID());
      }
      addProductToTuoteriviTable(joukko);

    }
    if (onkoVirheitä.contains(false))
      return false;
    return true;

  }

  public boolean luoVarastoliikenne(Varastoliikenne vl, ArrayList<Tuotejoukko> tjklist) {
    ArrayList<Boolean> errors = new ArrayList();
    errors.add(createVarastoliikenne(vl));
    int id = getVarastoliikenne_autoinc() - 1;
    for (Tuotejoukko tjk : tjklist) {
      Varastoliikennerivi vlr = new Varastoliikennerivi(tjk.getProduct().getID(), id,
          tjk.getMaara());
      System.out.println("IDIDID: " + (id));
      // vlr.setVarastoliikenneID(id);
      CreateVarastoliikennerivi(vlr);
      if (errorMsg != null) // Error viesti ei ole tyhjä eli virhe tapahtunut
        return false;
    }
    if (errors.contains(false)) {
      return false;
    }
    return true;
  }

  public boolean deleteVarastoliikennerivit(Product p) {
    return vrividb.deleteVarastoliikennerivit(p);
  }

  public int getVarastoliikenne_autoinc() {
    return varastoliikennedb.autoincrement();
  }

  public boolean createVarastoliikenne(Varastoliikenne vl) {
    return varastoliikennedb.createVarastoliikenne(vl);
  }

  public boolean CreateVarastoliikennerivi(Varastoliikennerivi vlr) {
    return vrividb.CreateVarastoliikennerivi(vlr);

  }

  public boolean addTemperatures(Product product) {
    return lampotiladb.addTemperatures(product);
  }

  public boolean createHyllypaikka(Hyllypaikka hyllypaikka) {
    return hyllydb.createHyllypaikka(hyllypaikka);
  }

  /**
   * Mikali tuotteelle on asetettu lampotila. Tarkistetaan voiko se lampotilansa perusteella sijaita
   * tuotejoukon sisaltamassa hyllypaikassa.
   *
   * @param tuotejoukko
   *          tuotejoukko, joka sisaltaa hyllypaikan ja tuotteen (Tuotejoukko)
   *
   * @return Mahtuu(true)/Ei mahdu(false) (Boolean)
   */

  public boolean checkLampotila(Tuotejoukko joukko) {
    boolean lampotila = true;
    if (joukko.getProduct().getMax_temperature() != null
        && joukko.getProduct().getMin_temperature() != null) {
      if (joukko.getHylly().getLämpötila() <= joukko.getProduct().getMax_temperature()
          && joukko.getHylly().getLämpötila() >= joukko.getProduct().getMin_temperature()) {
        System.out.println("Tuotteen lämpötila on sopiva hyllypaikkaan");
      } else {
        System.out.println("Tuotteen lämpötila vaatimus ei vastaa hyllyn lämpötilaa");
        lampotila = false;
      }
    } else {
      System.out.println("tuotteelle ei ole asetettu lämpötila rajoituksia");
    }
    return lampotila;
  }

  public Double haeHyllynKäytettyTilavuus(String hyllynimi) {
    double käytettytilavuus = 0;
    ArrayList<Tuotejoukko> tuotejoukot = haeHyllynTuotejoukot(hyllynimi);

    for (Tuotejoukko t : tuotejoukot) {
      käytettytilavuus = käytettytilavuus + t.getMaara() * t.getProduct().getProduct_volume();
    }

    return käytettytilavuus;
  }

  public Double haeHyllynKäytettyPaino(String hyllynimi) {
    double käytettypaino = 0;
    ArrayList<Tuotejoukko> tuotejoukot = haeHyllynTuotejoukot(hyllynimi);

    for (Tuotejoukko t : tuotejoukot) {
      käytettypaino = käytettypaino + t.getMaara() * t.getProduct().getProduct_weight();
    }

    return käytettypaino;
  }

  public Boolean NewProductInformationValidation(ArrayList<Product> products) {
    ArrayList<Boolean> booleans = new ArrayList();
    String fail_string = "";

    for (Product uusi : products) {
      ArrayList<Hyllypaikka> hpt = HaeTuotteenHyllypaikat(findProductWithID(uusi.getID()));
      for (Hyllypaikka hp : hpt) {
        // Tarkistetaan ensin tilavuuden muutos
        Product vanha = findProductWithID(uusi.getID());
        int maara = tuotteidenMaaraHyllyssa(vanha.getProduct_name(), hp.getNimi());
        System.out.println(uusi.toString());
        System.out.println(vanha.toString());

        double hyllyn_tilavuus = hp.getKorkeus() * hp.getLeveys() * hp.getPituus();
        double hyllyn_käytetty_tilavuus = haeHyllynKäytettyTilavuus(hp.getNimi());
        double hylly_jäljellä_oleva_tilavuus = hyllyn_tilavuus - hyllyn_käytetty_tilavuus;
        double uusien_tuotteiden_tilavuus = uusi.getProduct_length() * uusi.getProduct_width()
            * uusi.getProduct_height() * maara;
        double vanhojen_tuotteiden_tilavuus = vanha.getProduct_length() * vanha.getProduct_width()
            * vanha.getProduct_height() * maara;
        double tilavuuden_muutos = uusien_tuotteiden_tilavuus - vanhojen_tuotteiden_tilavuus;
        System.out.println("hylly_jäljellä_oleva_tilavuus = " + hylly_jäljellä_oleva_tilavuus
            + " tilavuuden_muutos = " + tilavuuden_muutos);

        if (tilavuuden_muutos > hylly_jäljellä_oleva_tilavuus) {
          // Tuotteet eivät mahdu hyllyyn
          booleans.add(false);
          fail_string = fail_string + "\nHyllyn tilavuus ei riitä, (tuotteen nimi: "
              + vanha.getProduct_name() + ", hyllypaikka: " + hp.getNimi() + ")";

        }
        // Tarkistetaan painon muutos
        double hyllyn_käytetty_paino = haeHyllynKäytettyPaino(hp.getNimi());
        double hyllyn_jäljellä_oleva_paino = hp.getMax_paino() - hyllyn_käytetty_paino;
        double uusien_tuotteiden_paino = uusi.getProduct_weight() * maara;
        double vanhojen_tuotteiden_paino = vanha.getProduct_weight() * maara;
        double paino_muutos = uusien_tuotteiden_paino - vanhojen_tuotteiden_paino;
        System.out.println("hyllyn_jäljellä_oleva_paino = " + hyllyn_jäljellä_oleva_paino
            + " paino_muutos = " + paino_muutos);
        if (paino_muutos > hyllyn_jäljellä_oleva_paino) {
          // Tuotteiden paino liian suuri hyllyyn
          booleans.add(false);
          fail_string = fail_string + "\nHyllyn painoraja ylittyy, (tuotteen nimi: "
              + vanha.getProduct_name() + ", hyllypaikka: " + hp.getNimi() + ")";
        }

        // Tarkistetaan lämpötilan muutos
        if (uusi.getTemp()) {
          if (hp.getLämpötila() >= uusi.getMax_temperature()) {
            booleans.add(false);
            fail_string = fail_string
                + "\nHyllyn lämpötila liian korkea tuotteelle, (tuotteen nimi: "
                + vanha.getProduct_name() + ", hyllypaikka: " + hp.getNimi() + ")";
          }
          if (hp.getLämpötila() <= uusi.getMin_temperature()) {
            booleans.add(false);
            fail_string = fail_string
                + "\nHyllyn lämpötila liian matala tuotteelle, (tuotteen nimi: "
                + vanha.getProduct_name() + ", hyllypaikka: " + hp.getNimi() + ")";
          }
        }

      }
    }

    if (booleans.contains(false)) {
      errorMsg = "Korjaa alla olevat virhekohdat: \n" + fail_string;
      return false;
    } else {
      updateProducts(products);
    }

    return true;
  }

  /**
   * Tarkistaa mahtuuko tuotteet hyllyyn
   *
   * @param tuotejoukko
   *          tuotejoukko, joka sisaltaa hyllypaikan ja tuotteen (Tuotejoukko)
   *
   * @return Mahtuu (true)/ei mahdu (false) (Boolean)
   */

  public boolean mahtuukoTuotteetHyllyyn(Tuotejoukko joukko) {
    boolean mahtuuko = true;

    Hyllypaikka hylly = haeHylly(joukko.getHylly().getNimi());

    Double hyllyn_tilavuus = hylly.getKorkeus() * hylly.getLeveys() * hylly.getPituus();
    Double vaadittu_tilavuus = joukko.getMaara() * joukko.getProduct().getProduct_volume();
    Double käytetty_tilavuus = 0.0;

    Double hyllyn_max_paino = haeHylly(joukko.getHylly().getNimi()).getMax_paino();
    Double vaadittu_paino = joukko.getMaara() * joukko.getProduct().getProduct_weight();
    Double käytetty_paino = 0.0;

    ArrayList<Tuotejoukko> hyllyntuotejoukot = haeHyllynTuotejoukot(joukko.getHylly().getNimi());

    for (Tuotejoukko t : hyllyntuotejoukot) {
      System.out.println("tj nimi = "+t.getProduct().getProduct_name());
      System.out.println("tj paino = " +t.getProduct().getProduct_weight()*t.getMaara());
      System.out.println("tj tilavuus = " +t.getProduct().getProduct_volume()*t.getMaara());
      käytetty_tilavuus = käytetty_tilavuus + t.getMaara() * t.getProduct().getProduct_volume();
      käytetty_paino = käytetty_paino + t.getMaara() * t.getProduct().getProduct_weight();
    }
    System.out.println("hyllyn_tilavuus =" + hyllyn_tilavuus + " vaadittu_tilavuus ="
        + vaadittu_tilavuus + " käytetty_tilavuus =" + käytetty_tilavuus);
    System.out.println("hyllyn_max_paino =" + hyllyn_max_paino + " vaadittu_paino ="
        + vaadittu_paino + " käytetty_paino =" + käytetty_paino);

    if ((hyllyn_tilavuus - käytetty_tilavuus) > vaadittu_tilavuus
        && (hyllyn_max_paino - käytetty_paino) > vaadittu_paino) {
      return mahtuuko;
    } else {
      mahtuuko = false;
    }
    return mahtuuko;

  }

  public Product findProductWithID(int id) {
    return productdb.findProductWithID(id);
  }

  public boolean addProduct(Product product) {
    return productdb.addProduct(product);
  }

  public ArrayList<Tuotejoukko> haeHyllynTuotejoukot(String hyllynimi) {
    return tuoterividb.haeHyllynTuotejoukot(hyllynimi);
  }

  public boolean addProductToTuoteriviTable(Tuotejoukko joukko) {
    return tuoterividb.addProductToTuoteriviTable(joukko);
  }

  public Hyllypaikka haeHylly(String tunnus) {
    return hyllydb.haeHylly(tunnus);
  }

  public boolean paivitaHylly(Hyllypaikka h) {
    return hyllydb.paivitaHyllynTiedot(h);
  }

  public ArrayList<Hyllypaikka> HaeTuotteenHyllypaikat(Product product) {
    return hyllydb.HaeTuotteenHyllypaikat(product);
  }

  public ArrayList<String> haeHyllypaikanTuotteet(String hyllypaikka) {
    return productdb.haeHyllypaikanTuotteet(hyllypaikka);
  }

  public Product findTemperatures(Product pro) {
    return lampotiladb.findTemperatures(pro);
  }

  public Product findProduct(String nimi) {
    return productdb.findProduct(nimi);
  }

  public int tuotteidenMaaraHyllyssa(String nimi, String hyllypaikka) {
    return tuoterividb.tuotteidenMaaraHyllyssa(nimi, hyllypaikka);
  }

  public boolean muokkaaTuoteriviä(Tuotejoukko tuotejoukko) {
    return tuoterividb.muokkaaTuoteriviä(tuotejoukko);
  }

  public ArrayList<Product> findProducts(String nimi) {
    return productdb.findProducts(nimi);
  }

  public boolean updateProducts(ArrayList<Product> products) {
    return productdb.updateProducts(products);
  }

  public boolean checkIfTuoteIDExcistInLampoTila(int ID) {
    return lampotiladb.checkIfTuoteIDExcistInLampoTila(ID);
  }

  public boolean deleteLampotila(Product p) {
    return lampotiladb.deleteLampotila(p);
  }

  public boolean updateLampotila(Product p) {
    return lampotiladb.updateLampotila(p);
  }

  public boolean addAsiakas(Asiakas a) {
    return asiakasdb.addAsiakas(a);
  }

  public boolean updateAsiakas(Asiakas a) {
    return asiakasdb.updateAsiakas(a);
  }

  public Asiakas haeAsiakas(String nimi) {
    return asiakasdb.haeAsiakasNimella(nimi);
  }

  public Asiakas haeAsiakas(int ID) {
    return asiakasdb.haeAsiakasNumerolla(ID);
  }

  public boolean deleteHyllypaikka(Hyllypaikka h) {
    return hyllydb.deleteHyllypaikka(h);
  }

  public boolean deleteAsiakas(Asiakas a) {
    return asiakasdb.deleteAsiakas(a);
  }

  public boolean deleteProduct(Product p) {
    return productdb.deleteProduct(p);
  }

  public boolean deleteTuoterivi(Tuotejoukko tj) {
    return tuoterividb.deleteTuoterivi(tj);
  }

  public ArrayList<Asiakas> haeAsiakkaat(String nimi) {
    return asiakasdb.haeAsiakkaat(nimi);
  }

  public ArrayList<Tuotejoukko> haeTuotteenKaikkiTuoterivit(Product p) {
    return tuoterividb.haeTuotteenKaikkiTuoterivit(p);
  }

  public List<Varastoliikenne> haeVarastoliikenteenRivit(int customerID){
    List<Varastoliikenne> res = new ArrayList<Varastoliikenne>();
    for(Varastoliikenne vl : varastoliikennedb.findVarastoliikenneRivit(customerID)){
       res.add(vrividb.findVarastoliikennerivit(vl));
    }
    return res;
  }

  public Varastoliikenne findVarastoliikennerivit(Varastoliikenne vl) {
    return vrividb.findVarastoliikennerivit(vl);
  }

  public void deleteRivitByID(int id) {
    vrividb.deleteRivitByID(id);
  }

  public Varastoliikenne findVarastoliikenne(int varastoliikenneid) {
    return varastoliikennedb.findVarastoliikenne(varastoliikenneid);
  }

  public boolean deleteLahetys(int id){
    if(!vrividb.deleteRivitByID(id))
      return false;
    return varastoliikennedb.deleteVarastoliikenne(id);
  }

  public boolean tallennaMuokattuLahetys(Varastoliikenne vl, int id){
    System.out.println(vl.getVarastoliikenneID());
    for(Varastoliikennerivi vlr : vl.getRivit()){
      System.out.println(vlr.getTuoteID() + " " + vlr.getMaara() + " " + vlr.getVarastoliikenneID());
    }
    if(!varastoliikennedb.paivitaVarastoliikenne(vl, id)){ //Päivitä yleiset tiedot
      return false;
    }
    vrividb.deleteRivitByID(vl.getVarastoliikenneID());//Poistetaan vanhat rivit
    for(Varastoliikennerivi vlr : vl.getRivit()){
      if(!vrividb.CreateVarastoliikennerivi(vlr)){ //luodaan uusilla tuoteriveillä
        return false;
      }
    }
    return true;
  }

  public Tuotejoukko haeTuotejoukkoHyllysta(String hyllynNimi, String tuotteenNimi){
    Tuotejoukko tj = null;
    Product tuote = productdb.findProduct(tuotteenNimi);
    if(tuote==null)
      return tj;
    Hyllypaikka hyllypaikka = hyllydb.haeHylly(hyllynNimi);
    if(hyllypaikka==null)
      return tj;
    tj = tuoterividb.haeTuotejoukko(hyllypaikka, tuote);
    return tj;
  }

  public TreeMap<Date, Integer> haeTietoja(Date d1, Date d2, int numero){
    return varastoliikennedb.haeTiedot(d1, d2, numero);
  }

  // /**
  // * Sulje tietokanta yhteys.
  // *
  // * @throws SQLException Heittää error, jos jostain syystä sulkeminen
  // epäonnistuu
  // */
  //

  public void close() throws SQLException {
    conn.close();
  }


}
