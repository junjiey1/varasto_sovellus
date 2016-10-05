import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import vPakkaus.Product;

public class Product_Test {

	private Product pro;

	@BeforeClass
	public static void Valmistelut() {
		System.out.println("------Product_Test Suite------");
	}

	@Before
	public void nollaus(){
		pro = new Product("Testi", "AA", 3.45, 2.4,2f, 1);
	}

	@Test
	public void test_Get_JA_Set_Name() {
		assertEquals("Nimen haku ei toimi" , pro.getProduct_name(),"Testi");
		pro.setProduct_name("UUSINIMI");
		assertEquals("Nimen haku ei toimi" , pro.getProduct_name(),"UUSINIMI");
	}

	@Test
	public void test_Get_JA_Set_Hyllypaikka() {
		assertEquals("Hyllypaikan haku ei toimi" , pro.getProduct_location(),"AA");
		pro.setProduct_location("CC");
		assertEquals("Hyllypaikan asetus ei toimi" , pro.getProduct_location(),"CC");
	}

	@Test
	public void test_Get_JA_Set_PainoTilavuus(){
		assertEquals("Painon haku ei toimi" ,pro.getProduct_weight(),3.45,0.0);
		assertEquals("Tilavuuden haku ei toimi" ,pro.getProduct_volume(),2.4,0.0);
		pro.setProduct_weight(900.25);
		assertEquals("Painon asetus ei toimi" , pro.getProduct_weight(),900.25,0.0);
		pro.setProduct_volume(298.41);
		assertEquals("Tilavuuden asetus ei toimi" , pro.getProduct_volume(),298.41,0.0);
	}

	@Test
	public void test_Get_JA_Set_Hinta() {
		assertEquals("Hinnan haku ei toimi" , pro.getProduct_price(),2f,0.0);
		pro.setProduct_price(39.5f);
		assertEquals("Hinnan asetus ei toimi" , pro.getProduct_price(),39.5f,0.0);
	}

}
