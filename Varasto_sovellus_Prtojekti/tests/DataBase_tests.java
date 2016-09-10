import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.After;
import org.junit.Test;

public class DataBase_tests {

	private Connection conn = null;

	@After
	public void sh()
	{
		try{conn.close();}catch(Exception e){}
	}

	@Test
	public void test() {
		System.out.println("Yhdistä tietokantaan...");
		boolean error = false;
		try{
			 Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e){
			error = true;
			System.out.println("JDBC-ajurin lataus epäonnistui");
		}
		assertEquals("Tietokantaan yhdistäminen ei toimi!",error,false);
	}

}
