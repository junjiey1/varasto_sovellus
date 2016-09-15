import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import vPakkaus.DB_AccessObject;

public class DataBase_tests {

	private Connection conn = null;
	private static DB_AccessObject db;

	@AfterClass
	public static void sh()
	{
		System.out.println("------SULJETAAN TIETOKANTAYHTEYS------");
		try{db.close();}catch(Exception e){}
	}

	@BeforeClass
	public static void Valmistelut()
	{
		System.out.println("------ESIVALMISTELUT-------");
		db = new DB_AccessObject();
	}

	@Test
	public void LogIn_V��r�tTunnukset()
	{
		System.out.println("\nTest : LogIn_V��r�tTunnukset()\n");
		boolean result = db.LogIn("randomia", "igszsg");
		assertEquals("LogIn_V��r�tTunnukset() testi EP�ONNISTUI!",result,false);
	}

	@Test
	public void LogIn_AidotTunnukset()
	{
		System.out.println("\nTest : LogIn_AidotTunnukset()\n");
		boolean result = db.LogIn("julle", "juu");
		assertEquals("LogIn_AidotTunnukset() testi EP�ONNISTUI!",result,true);
	}

}
