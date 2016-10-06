import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import vPakkaus.DB_AccessObject;
import vPakkaus.Product;

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

	@BeforeClass
	public static void Valmistelut() {
		System.out.println("------ESIVALMISTELUT-------");
		db = new DB_AccessObject();
		db.dropTuotteet();
	}

	@Before
	public void PoistaTuotteet() {
		db = new DB_AccessObject();
		db.dropTuotteet();
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

	@Test
	public void Etsi_Tavara() {
		System.out.println("\nTest : Etsi_Tavara()");
		System.out.println("Test : Lisätään tavara etsintää varten");
		boolean result = db.Lisaa("TEST-ITEM", 1.2, 3.6, "testipaikka", 2.2f, 1);
		assertEquals("Tavaran lisääminen ONNISTUI!", result, true);

		Product product = db.findProduct("TEST-ITEM");
		Product product_test = new Product("TEST-ITEM", "testipaikka", 1.2, 3.6, 2.2f, 1);
		assertEquals("Tavaran etsiminen ONNISTUI!", product.toString(), product_test.toString());
	}

	@Test
	public void Etsi_Tavarat() {
		System.out.println("\nTest : Etsi_Tavarat()");
		System.out.println("Test : Lisätään tavarat etsintää varten");
		boolean result = db.Lisaa("TEST-ITEM1", 1.2, 3.6, "testipaikka", 2.2f, 1);
		assertEquals("Tavaran lisääminen ONNISTUI!", result, true);
		result = db.Lisaa("TEST-ITEM2", 1.2, 3.6, "testipaikka", 2.2f, 1);
		assertEquals("Tavaran lisääminen ONNISTUI!", result, true);

		ArrayList<Product> productlist_handmade = new ArrayList();
		ArrayList<Product> productlist = db.findProducts("TES");

		Product product_test1 = new Product("TEST-ITEM1", "testipaikka", 1.2, 3.6, 2.2f, 1);
		Product product_test2 = new Product("TEST-ITEM2", "testipaikka", 1.2, 3.6, 2.2f, 1);

		productlist_handmade.add(product_test1);
		productlist_handmade.add(product_test2);

		int i = 0;
		for (Product p : productlist) {
			productlist_handmade.get(i).setID(p.getID());
			System.out.println(p.toString());
			System.out.println("equals");
			System.out.println(productlist_handmade.get(i).toString());
			System.out.println(" ");
			assertEquals("Tavaroiden haku ONNISTUI!", p.toString(), productlist_handmade.get(i).toString());
			i++;
		}
	}
	
	

	@Test
	public void Tavaran_Lisääminen_Oikeilla_Parametreilla() {
		System.out.println("\nTest : Tavaran_Lisääminen_Oikeilla_Parametreilla()");
		boolean result = db.Lisaa("JUNIT-TEST-ITEM", 1.2, 3.6, "JUNIT", 2.2f, 1);
		assertEquals("Tavaran lisääminen EPÄONNISTUI!", result, true);
		result = db.Lisaa("JUNIT-TEST-ITEM", 1.2, 3.6, "JUNIT", 2.2f, 1);
		assertEquals("Duplicate tuote lisättiin tietokantaan eli testi EPÄONNISTUI!", result, false);
	}


	@Test
	public void Tavaran_Etsiminen() {
		System.out.println("\nTavaran etsiminen");
		System.out.println("\nTest : Tavaran_Lisääminen_Oikeilla_Parametreilla()");
		boolean result = db.Lisaa("PRODUT_JUNIT", 1.2, 3.6, "JUNIT_ETSI", 2.2f, 1);
		assertEquals("Tavaran lisääminen EPÄONNISTUI!", result, true);
		Product p = db.findProduct("PRODUT_JUNIT");
		assertEquals("Tavaran lisääminen EPÄONNISTUI!", 2.2f, p.getProduct_price(), 0.0);
		assertEquals("Tavaran lisääminen EPÄONNISTUI!", 1.2, p.getProduct_weight(), 0.0);
		assertEquals("Tavaran lisääminen EPÄONNISTUI!", p.getProduct_name(), "PRODUT_JUNIT");
	}


    // Testimetodin nimi voi olla mitä tahansa, edessä annotaatio @Test


}
