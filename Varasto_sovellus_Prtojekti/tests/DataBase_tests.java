import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import vPakkaus.DB_AccessObject;
import vPakkaus.Hyllypaikka;
import vPakkaus.Product;
import vPakkaus.Tuotejoukko;

public class DataBase_tests {

	private Connection conn = null;
	private static DB_AccessObject db;

	@AfterClass
	public static void sh() {
		System.out.println("------SULJETAAN TIETOKANTAYHTEYS------");
		try {
			db.close();
		} catch (Exception e) {
		}
	}

	@Before
	public void luoDB_AccesObject() {
		db = new DB_AccessObject();
		}


	@Test
	public void LogIn_VäärätTunnukset() {
		System.out.println("\nTest : LogIn_VäärätTunnukset()\n");
		int result = db.LogIn("randomia", "igszsg")[0];
		assertEquals("LogIn_VäärätTunnukset() testi EPÄONNISTUI!", result, 0);
	}

	@Test
	public void LogIn_KäyttäjänID() {
		System.out.println("\nTest : LogIn_KäyttäjänID()");
		assertEquals("LogIn_AidotTunnukset() testi EPÄONNISTUI!", db.LogIn("testi", "testi")[1], 7);
	}

	@Test
	public void LogIn_AidotTunnukset() {
		System.out.println("\nTest : LogIn_AidotTunnukset()\n");
		int result = db.LogIn("testi", "testi")[0];
		assertEquals("LogIn_AidotTunnukset() testi EPÄONNISTUI!", result, 1);
	}

//	@Test
//	public void Lisaa_Tuote_tableen() {
//	System.out.println("\nTest : Lisaa_Tuote_tableen\n");
//	Product product = new Product("Kakka", 1.0, 2.0, 3.0, 4.0, 5.0f);
//	boolean result = db.addProductToTuoteTable(product);
//	assertEquals("Lisaaminen tuotetauluun", result, true);
//	}

	@Test
	public void Lisaa_Tuote_tableen_lampotilojen_kanssa() {
	System.out.println("\nTest : Lisaa_Tuote_tableen_lampotilojen_kanssa\n");
	Product product = new Product("Kakka", 1.0, 2.0, 3.0, 4.0, 5.0f);
	product.setID(537);
	product.setMax_temperature(10);
	product.setMin_temperature(-12);
	boolean result = db.addProductToTuoteTable(product);
	assertEquals("Lisaaminen tuotetauluun", result, true);
	}


















































//	@Test
//	public void Lisaa_tuote() {
//		System.out.println("\nTest : Lisaa_Tuote\n");
//		Product product = new Product("Kakka", 1.0, 2.0, 3.0, 4.0, 5.0f);
//		Hyllypaikka hyllypaikka = new Hyllypaikka("a-15");
//		int maara = 5;
//		Tuotejoukko joukko = new Tuotejoukko(product, hyllypaikka, maara);
//		boolean result = db.Lisaa(joukko);
//		assertEquals("Tavaran lisaaminen onnistui!", result, false);
//	}

//	@Test
//	public void Etsi_Tavara() {
//		System.out.println("\nTest : Etsi_Tavara()");
//		System.out.println("Test : Lisätään tavara etsintää varten");
//		boolean result = db.Lisaa("TEST-ITEM", 1.2, 3.6, "testipaikka", 2.2f, 1);
//		assertEquals("Tavaran lisääminen ONNISTUI!", result, true);
//
//		Product product = db.findProduct("TEST-ITEM");
//		Product product_test = new Product("TEST-ITEM", "testipaikka", 1.2, 3.6, 2.2f, 1);
//		product_test.setID(product.getID());
//		assertEquals("Tavaran etsiminen ONNISTUI!", product.toString(), product_test.toString());
//	}
//
//	@Test
//	public void Etsi_Tavarat() {
//		System.out.println("\nTest : Etsi_Tavarat()");
//		System.out.println("Test : Lisätään tavarat etsintää varten");
//		boolean result = db.Lisaa("TEST-ITEM1", 1.2, 3.6, "testipaikka", 2.2f, 1);
//		assertEquals("Tavaran lisääminen ONNISTUI!", result, true);
//		result = db.Lisaa("TEST-ITEM2", 1.2, 3.6, "testipaikka", 2.2f, 1);
//		assertEquals("Tavaran lisääminen ONNISTUI!", result, true);
//
//		ArrayList<Product> productlist_handmade = new ArrayList();
//		ArrayList<Product> productlist = db.findProducts("TES");
//
//		Product product_test1 = new Product("TEST-ITEM1", "testipaikka", 1.2, 3.6, 2.2f, 1);
//		Product product_test2 = new Product("TEST-ITEM2", "testipaikka", 1.2, 3.6, 2.2f, 1);
//
//		productlist_handmade.add(product_test1);
//		productlist_handmade.add(product_test2);
//
//		int i = 0;
//		for (Product p : productlist) {
//			productlist_handmade.get(i).setID(p.getID());
//			System.out.println(p.toString());
//			System.out.println("equals");
//			System.out.println(productlist_handmade.get(i).toString());
//			System.out.println(" ");
//			assertEquals("Tavaroiden haku ONNISTUI!", p.toString(), productlist_handmade.get(i).toString());
//			i++;
//		}
//	}
//
//	@Test
//	public void Poista_tavara() {
//
//		System.out.println("Test : Poista_tavara");
//		System.out.println("Lisätään tavara");
//		boolean result = db.Lisaa("TEST-ITEM", 1.2, 3.6, "testipaikka", 2.2f, 1);
//		assertEquals("Tavaran lisääminen ONNISTUI!", result, true);
//
//		System.out.println("Etsitään tavara");
//		Product product = db.findProduct("TEST-ITEM");
//		assertEquals("Tavaran haku ONNISTUI!", product.getProduct_name() , "TEST-ITEM");
//
//		System.out.println("Poistetaan tavara "+product.getProduct_name() +" "+ product.getID());
//		result = db.deleteProduct(product.getID());
//		db.deleteProduct(product.getID());
//		assertEquals("Tavaran poisto ONNISTUI!", result , true);
//
//		System.out.println("Etsitään tavara");
//		product = db.findProduct("TEST-ITEM");
//		assertEquals("Tavaran haku ONNISTUI!", product , null);
//
//	}
//
//	@Test
//	public void Tavaran_Lisääminen_Oikeilla_Parametreilla() {
//		System.out.println("\nTest : Tavaran_Lisääminen_Oikeilla_Parametreilla()");
//		boolean result = db.Lisaa("JUNIT-TEST-ITEM_JENKINS", 1.2, 3.6, "JUNIT", 2.2f, 1);
//		assertEquals("Tavaran lisääminen EPÄONNISTUI!", result, true);
//		result = db.Lisaa("JUNIT-TEST-ITEM_JENKINS", 1.2, 3.6, "JUNIT", 2.2f, 1);
//		assertEquals("Duplicate tuote lisättiin tietokantaan eli testi EPÄONNISTUI!", result, false);
//	}
//
//
//	@Test
//	public void Tavaran_Etsiminen() {
//		System.out.println("\nTavaran etsiminen");
//		System.out.println("\nTest : Tavaran_Lisääminen_Oikeilla_Parametreilla()");
//		boolean result = db.Lisaa("TEST-ITEM", 1.2, 3.6, "JUNIT_ETSI", 2.2f, 1);
//		assertEquals("Tavaran lisääminen EPÄONNISTUI!", result, true);
//		Product p = db.findProduct("TEST-ITEM");
//		assertEquals("Tavaran lisääminen EPÄONNISTUI!", 2.2f, p.getProduct_price(), 0.0);
//		assertEquals("Tavaran lisääminen EPÄONNISTUI!", 1.2, p.getProduct_weight(), 0.0);
//		assertEquals("Tavaran lisääminen EPÄONNISTUI!", p.getProduct_name(), "TEST-ITEM");
//	}
//

    // Testimetodin nimi voi olla mitä tahansa, edessä annotaatio @Test


}
