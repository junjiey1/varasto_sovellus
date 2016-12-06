import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import TietokantaKyselyt.AsiakasDB;
import TietokantaKyselyt.HyllyDB;
import TietokantaKyselyt.LampotilaDB;
import TietokantaKyselyt.ProductDB;
import TietokantaKyselyt.TuoteriviDB;
import TietokantaKyselyt.UsersDB;
import vPakkaus.Asiakas;
import vPakkaus.DB_AccessObject;
import vPakkaus.Hyllypaikka;
import vPakkaus.Product;
import vPakkaus.Tuotejoukko;
import vPakkaus.Varastoliikenne;
import vPakkaus.Varastoliikennerivi;

public class DataBase_tests {

  private static DB_AccessObject db;

  @AfterClass
  public static void sh() {
    System.out.println("------SULJETAAN TIETOKANTAYHTEYS------");
    try {
      db.close();
    } catch (Exception e) {

    }
  }

  @BeforeClass
  public static void luoDB_AccesObject() {
    db = new DB_AccessObject();
  }

  @Before
  public void Luo_testi_itemit() {
    System.out.println("\n Luodaan test itemit \n");

    Product tp1 = new Product("test_item_1", 1.0, 2.0, 2.0, 2.0, 2.0f);
    tp1.setMax_temperature(10);
    tp1.setMin_temperature(0);

    Product tp2 = new Product("test_item_2", 4.0, 4.0, 4.0, 4.0, 2.0f);
    tp2.setMax_temperature(10);
    tp2.setMin_temperature(0);

    Hyllypaikka hyllypaikka = new Hyllypaikka("hylly_test_1", 10.0, 10.0, 10.0, 5, 200);
    db.createHyllypaikka(hyllypaikka);

    Tuotejoukko joukko_1 = new Tuotejoukko(tp1, hyllypaikka, 3);
    Tuotejoukko joukko_2 = new Tuotejoukko(tp2, hyllypaikka, 2);
    db.lisaa(joukko_1);
    db.lisaa(joukko_2);

  }

  @After
  public void Poista_testi_itemit() {

    Hyllypaikka hp = db.haeHylly("hylly_test_1");
    Product p1 = db.findProduct("test_item_1");
    Product p2 = db.findProduct("test_item_2");
    Tuotejoukko tj1 = new Tuotejoukko(p1, hp, 0);
    Tuotejoukko tj2 = new Tuotejoukko(p2, hp, 0);

    db.deleteVarastoliikennerivit(p1);
    db.deleteVarastoliikennerivit(p2);

    db.deleteLampotila(p1);
    db.deleteLampotila(p2);

    db.deleteTuoterivi(tj1);
    db.deleteTuoterivi(tj2);

    db.deleteHyllypaikka(hp);

    db.deleteProduct(p1);
    db.deleteProduct(p2);

  }

  @Test
  public void LogIn_VäärätTunnukset() {
    System.out.println("\nTest : LogIn_VäärätTunnukset()\n");
    int result = db.logIn("randomia", "igszsg")[0];
    assertEquals("LogIn_VäärätTunnukset() testi EPÄONNISTUI!", result, 0);
  }

  @Test
  public void LogIn_KäyttäjänID() {
    System.out.println("\nTest : LogIn_KäyttäjänID()");
    assertEquals("LogIn_AidotTunnukset() testi EPÄONNISTUI!", db.logIn("testi", "testi")[1], 7);
  }

  @Test
  public void LogIn_AidotTunnukset() {
    System.out.println("\nTest : LogIn_AidotTunnukset()\n");
    int result = db.logIn("testi", "testi")[0];
    assertEquals("LogIn_AidotTunnukset() testi EPÄONNISTUI!", result, 1);
  }

  @Test
  public void Create_and_Delete_hyllypaikka_sb() {
    boolean res;
    System.out.println("\nTest : Create_and_delete_hyllypaikka\n");
    Hyllypaikka hyllypaikka = new Hyllypaikka("hylly_test_1_1", 10.0, 10.0, 10.0, 5, 200);
    if (db.haeHylly("hylly_test_1_1") == null) {
      res = db.createHyllypaikka(hyllypaikka);
      assertEquals("Hyllypaikan lisaaminen tietokantaan onnistui!", res, true);
    }
    res = db.deleteHyllypaikka(hyllypaikka);
    assertEquals("Hyllypaikan poistaminen tietokannasta onnistui!", res, true);
  }

  @Test
  public void Hae_hyllypaikka() {
    System.out.println("\nTest : Hae_hyllypaikka\n");
    Hyllypaikka hyllypaikka = new Hyllypaikka("hylly_test_1_1", 10.0, 10.0, 10.0, 5, 200);
    db.createHyllypaikka(hyllypaikka);
    Hyllypaikka h = db.haeHylly("hylly_test_1_1");
    assertEquals("Hyllypaikan hakeminen onnistui!", hyllypaikka.toString(), h.toString());
  }

  @Test
  public void HaeTuotteenHyllypaikat() {
    System.out.println("\nTest : HaeTuotteenHyllypaikat()\n");
    Product tp1 = new Product("test_item_99", 1.0, 2.0, 2.0, 2.0, 2.0f);
    ;
    Hyllypaikka hyllypaikka1 = new Hyllypaikka("hylly_test_98", 10.0, 10.0, 10.0, 5, 200);
    Hyllypaikka hyllypaikka2 = new Hyllypaikka("hylly_test_99", 10.0, 10.0, 10.0, 5, 200);
    db.createHyllypaikka(hyllypaikka1);
    db.createHyllypaikka(hyllypaikka2);

    Tuotejoukko joukko_1 = new Tuotejoukko(tp1, hyllypaikka1, 2);
    Tuotejoukko joukko_2 = new Tuotejoukko(tp1, hyllypaikka2, 2);
    db.lisaa(joukko_1);
    db.lisaa(joukko_2);

    ArrayList<Hyllypaikka> list = db.HaeTuotteenHyllypaikat(tp1);
    assertEquals("Hyllypaikkojen hakeminen onnistui!", 2, list.size());

    Product tp2 = db.findProduct("test_item_99");
    Tuotejoukko joukko_3 = new Tuotejoukko(tp2, hyllypaikka1, 2);
    Tuotejoukko joukko_4 = new Tuotejoukko(tp2, hyllypaikka2, 2);

    db.deleteTuoterivi(joukko_3);
    db.deleteTuoterivi(joukko_4);
    db.deleteProduct(tp2);
    db.deleteHyllypaikka(hyllypaikka1);
    db.deleteHyllypaikka(hyllypaikka2);

  }

  @Test
  public void add_and_delete_Product() {
    System.out.println("\nTest : add_and_delete_Product()\n");
    Product p = new Product("test_item_99", 1.0, 2.0, 2.0, 2.0, 2.0f);
    boolean res = db.addProduct(p);
    assertEquals("Tuotteen lisääminen onnistui!", true, res);
    res = db.deleteProduct(p);
    assertEquals("Tuotteen lisääminen onnistui!", true, res);
  }

//  @Test
//  public void update_Product() {
//    System.out.println("\nTest : update_Product()\n");
//    Product p1 = new Product("test_item_101", 1.0, 2.0, 2.0, 2.0, 2.0f);
//    Product p2 = new Product("test_item_100", 1.0, 2.0, 2.0, 2.0, 2.0f);
//    db.addProduct(p1);
//    db.addProduct(p2);
//    p1.setProduct_weight(20.0);
//    p2.setProduct_weight(20.0);
//    ArrayList<Product> list = new ArrayList();
//    list.add(p1);
//    list.add(p2);
//    boolean res = db.updateProducts(list);
//    ArrayList<Product> list1 = db.findProducts("test_item_10");
//    for (int i = 0; i < list.size(); i++) {
//      assertEquals("juuh", 20.0, list1.get(i).getProduct_weight());
//    }
//
//    db.deleteProduct(p1);
//    db.deleteProduct(p2);

//  }

  // @Test
  // public void add_and_delete_Temperature() {
  //
  // System.out.println("\nTest : addTemperature()\n");
  // Product p = new Product("test_item_33", 4.0, 4.0, 4.0, 4.0, 2.0f);
  //
  // boolean res = db.addTemperatures(p);
  // assertEquals("Lampotilojen lisays tuotteelle onnistui!", res, true);
  // res = db.deleteLampotila(p);
  // assertEquals("Tuotteen lampotilojen poistaminen onnistui!", res, true);
  // }

  @Test
  public void PaivitaHyllypaikka() {
    System.out.println("\nTest : PaivitaHyllypaikka()\n");
    Hyllypaikka h = db.haeHylly("hylly_test_1");
    h.setMax_paino(300);
    db.paivitaHylly(h);
    Hyllypaikka h1 = db.haeHylly("hylly_test_1");
    assertEquals(0.01, 300.0, h1.getMax_paino());
  }

  // @Test
  // public void Lisaa_Tuote_tableen_lampotilojen_kanssa() {
  // System.out.println("\nTest : Lisaa_Tuote_tableen_lampotilojen_kanssa\n");
  // Product product = new Product("Kakka", 1.0, 2.0, 3.0, 4.0, 5.0f);
  // product.setID(537);
  // product.setMax_temperature(10);
  // product.setMin_temperature(-12);
  // boolean result = db.addProductToTuoteTable(product);
  // assertEquals("Lisaaminen tuotetauluun", result, true);
  // }

  // @Test
  // public void createHyllypaikka() {
  // System.out.println("\nTest : createHyllypaikka\n");
  // Hyllypaikka hyllypaikka = new Hyllypaikka("a-3", 100, 100, 100, -5, 2000);
  // boolean result = db.CreateHyllypaikka(hyllypaikka);
  // assertEquals("Hyllypaikan luominen onnistui", result, true);
  // }

  @Test
  public void Varastoliikenne_luonti() {
    System.out.println("\nTest : Varastoliikenne_luonti\n");
    Varastoliikenne vl = new Varastoliikenne(1, new Date(2016 - 1900, 10 - 1, 10), "osoite", 6, 11);
    ArrayList<Tuotejoukko> lista = new ArrayList();

    Hyllypaikka hp = db.haeHylly("hylly_test_1");
    Product p1 = db.findProduct("test_item_1");
    Product p2 = db.findProduct("test_item_2");
    Tuotejoukko tj1 = new Tuotejoukko(p1, hp, 1);
    Tuotejoukko tj2 = new Tuotejoukko(p2, hp, 2);

    lista.add(tj1);
    lista.add(tj2);

    boolean res = db.luoVarastoliikenne(vl, lista);
    assertEquals("Varastoliikenteen luonti onnistui!", true, res);

  }

  @Test
  public void MahtuukoTuotteetHyllyyn_test() {
    System.out.println("\nTest : MahtuukoTuotteetHyllyyn_test\n");
    Hyllypaikka hp = db.haeHylly("hylly_test_1");
    Product p1 = db.findProduct("test_item_1");

    Tuotejoukko tj1 = new Tuotejoukko(p1, hp, 105);
    Tuotejoukko tj2 = new Tuotejoukko(p1, hp, 107);
    boolean result = db.mahtuukoTuotteetHyllyyn(tj1);
    assertEquals("Tuotteet mahtuvat hyllyyn", true, result);
    result = db.mahtuukoTuotteetHyllyyn(tj2);
    assertEquals("Tuotteet eivät mahdu hyllyyn!", false, result);
  }

  public void haeHylly() {

  }

  @Test
  public void haeAsiakkaat() {
    System.out.println("\nTest : haeAsiakkaat()\n");
    Asiakas a1 = new Asiakas("Seppo oy1", "Kuskinkatu 3 a 14", "Helsinki", "Sepi@Sepi.fi",
        "0440330990", "00770");
    Asiakas a2 = new Asiakas("Seppo oy2", "Kuskinkatu 3 a 14", "Helsinki", "Sepi@Sepi.fi",
        "0440330990", "00770");
    db.addAsiakas(a1);
    db.addAsiakas(a2);
    ArrayList<Asiakas> asiakkaat = db.haeAsiakkaat("seppo");
    for (Asiakas a : asiakkaat) {
      a.toString();
    }
    int maara = asiakkaat.size();
    assertEquals("Asiakkaiden haku toimii!", 2, maara);
    db.deleteAsiakas(a1);
    db.deleteAsiakas(a2);
  }

  @Test
  public void AddAndDeleteAsiakas() {
    System.out.println("\nTest : AddAndDeleteAsiakas()\n");

    Asiakas a = new Asiakas("Seppo oy", "Kuskinkatu 3 a 14", "Helsinki", "Sepi@Sepi.fi",
        "0440330990", "00770");
    boolean res = db.addAsiakas(a);
    assertEquals("Asiakas lisätty onnistuneesti!", true, res);
    a.setEmai("Supikoira@Seppo.fi");
    res = db.updateAsiakas(a);
    assertEquals("Asiakkaan tietoja päivitetty onnistuneesti!", true, res);
    res = db.deleteAsiakas(a);
    assertEquals("Asiakas poistettu onnistuneesti!", true, res);
  }

  @Test
  public void findAsiakas() {
    System.out.println("\nTest : findAsiakas()\n");
    Asiakas a = new Asiakas("Seppo oy", "Kuskinkatu 3 a 14", "Helsinki", "Sepi@Sepi.fi",
        "0440330990", "00770");
    boolean res = db.addAsiakas(a);
    assertEquals("Asiakas lisätty onnistuneesti!", true, res);

    Asiakas a1 = db.haeAsiakas(a.getNimi());
    a.setID(a1.getID());

    assertEquals("Asiakkaan haku onnistui!", a.toString(), a1.toString());

    res = db.deleteAsiakas(a1);
    assertEquals("Asiakas poistettu onnistuneesti!", true, res);
  }

  // @Test
  // public void Lisaa_tuote() {
  //
  // System.out.println("\nTest : Lisaa_Tuote\n");
  // Product product = new Product("testi_tuote_121", 1.0, 1.0, 1.0, 1.0, 1.0f);
  // product.setMax_temperature(10);
  // product.setMin_temperature(0);
  // Hyllypaikka hyllypaikka = new Hyllypaikka("hylly_test_121", 10.0, 10.0, 10.0, 5, 200);
  // Tuotejoukko joukko = new Tuotejoukko(product, hyllypaikka, 2);
  // boolean result = db.lisaa(joukko);
  // assertEquals("Tavaran lisaaminen onnistui!", true, result);
  // product = db.findProduct("testi_tuote_121");
  // joukko = new Tuotejoukko(product, hyllypaikka, 2);
  //
  // db.deleteLampotila(product);
  // db.deleteTuoterivi(joukko);
  // db.deleteHyllypaikka(hyllypaikka);
  //
  // }

  // @Test
  // public void hae_hyllyn_tuotejoukot() {
  // ArrayList <Tuotejoukko> tj = db.haeHyllynTuotejoukot("a-1");
  // for (Tuotejoukko t : tj) {
  // System.out.println(t.getProduct().getProduct_name());
  // }
  // assertEquals("Tavaran lisaaminen onnistui!", tj, true);
  // }

  // @Test
  // public void Etsi_Tavara() {
  // System.out.println("\nTest : Etsi_Tavara()");
  // System.out.println("Test : Lisätään tavara etsintää varten");
  // boolean result = db.Lisaa("TEST-ITEM", 1.2, 3.6, "testipaikka", 2.2f, 1);
  // assertEquals("Tavaran lisääminen ONNISTUI!", result, true);
  //
  // Product product = db.findProduct("TEST-ITEM");
  // Product product_test = new Product("TEST-ITEM", "testipaikka", 1.2, 3.6, 2.2f, 1);
  // product_test.setID(product.getID());
  // assertEquals("Tavaran etsiminen ONNISTUI!", product.toString(), product_test.toString());
  // }
  //
  // @Test
  // public void Etsi_Tavarat() {
  // System.out.println("\nTest : Etsi_Tavarat()");
  // System.out.println("Test : Lisätään tavarat etsintää varten");
  // boolean result = db.Lisaa("TEST-ITEM1", 1.2, 3.6, "testipaikka", 2.2f, 1);
  // assertEquals("Tavaran lisääminen ONNISTUI!", result, true);
  // result = db.Lisaa("TEST-ITEM2", 1.2, 3.6, "testipaikka", 2.2f, 1);
  // assertEquals("Tavaran lisääminen ONNISTUI!", result, true);
  //
  // ArrayList<Product> productlist_handmade = new ArrayList();
  // ArrayList<Product> productlist = db.findProducts("TES");
  //
  // Product product_test1 = new Product("TEST-ITEM1", "testipaikka", 1.2, 3.6, 2.2f, 1);
  // Product product_test2 = new Product("TEST-ITEM2", "testipaikka", 1.2, 3.6, 2.2f, 1);
  //
  // productlist_handmade.add(product_test1);
  // productlist_handmade.add(product_test2);
  //
  // int i = 0;
  // for (Product p : productlist) {
  // productlist_handmade.get(i).setID(p.getID());
  // System.out.println(p.toString());
  // System.out.println("equals");
  // System.out.println(productlist_handmade.get(i).toString());
  // System.out.println(" ");
  // assertEquals("Tavaroiden haku ONNISTUI!", p.toString(),
  // productlist_handmade.get(i).toString());
  // i++;
  // }
  // }
  //
  // @Test
  // public void Poista_tavara() {
  //
  // System.out.println("Test : Poista_tavara");
  // System.out.println("Lisätään tavara");
  // boolean result = db.Lisaa("TEST-ITEM", 1.2, 3.6, "testipaikka", 2.2f, 1);
  // assertEquals("Tavaran lisääminen ONNISTUI!", result, true);
  //
  // System.out.println("Etsitään tavara");
  // Product product = db.findProduct("TEST-ITEM");
  // assertEquals("Tavaran haku ONNISTUI!", product.getProduct_name() , "TEST-ITEM");
  //
  // System.out.println("Poistetaan tavara "+product.getProduct_name() +" "+ product.getID());
  // result = db.deleteProduct(product.getID());
  // db.deleteProduct(product.getID());
  // assertEquals("Tavaran poisto ONNISTUI!", result , true);
  //
  // System.out.println("Etsitään tavara");
  // product = db.findProduct("TEST-ITEM");
  // assertEquals("Tavaran haku ONNISTUI!", product , null);
  //
  // }
  //
  // @Test
  // public void Tavaran_Lisääminen_Oikeilla_Parametreilla() {
  // System.out.println("\nTest : Tavaran_Lisääminen_Oikeilla_Parametreilla()");
  // boolean result = db.Lisaa("JUNIT-TEST-ITEM_JENKINS", 1.2, 3.6, "JUNIT", 2.2f, 1);
  // assertEquals("Tavaran lisääminen EPÄONNISTUI!", result, true);
  // result = db.Lisaa("JUNIT-TEST-ITEM_JENKINS", 1.2, 3.6, "JUNIT", 2.2f, 1);
  // assertEquals("Duplicate tuote lisättiin tietokantaan eli testi EPÄONNISTUI!", result, false);
  // }
  //
  //
  // @Test
  // public void Tavaran_Etsiminen() {
  // System.out.println("\nTavaran etsiminen");
  // System.out.println("\nTest : Tavaran_Lisääminen_Oikeilla_Parametreilla()");
  // boolean result = db.Lisaa("TEST-ITEM", 1.2, 3.6, "JUNIT_ETSI", 2.2f, 1);
  // assertEquals("Tavaran lisääminen EPÄONNISTUI!", result, true);
  // Product p = db.findProduct("TEST-ITEM");
  // assertEquals("Tavaran lisääminen EPÄONNISTUI!", 2.2f, p.getProduct_price(), 0.0);
  // assertEquals("Tavaran lisääminen EPÄONNISTUI!", 1.2, p.getProduct_weight(), 0.0);
  // assertEquals("Tavaran lisääminen EPÄONNISTUI!", p.getProduct_name(), "TEST-ITEM");
  // }
  //

  // Testimetodin nimi voi olla mitä tahansa, edessä annotaatio @Test

}
