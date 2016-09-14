package vPakkaus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DB_AccessObject {
	//ACCESS SQL_DB_OBJ.
	private static Connection conn = null;

	public DB_AccessObject()
	{
		/////////////////Tämä osio vain testailua varten
		Scanner s = new Scanner(System.in);
		System.out.println("Kenen koneella ollaan?\n1.Julius\n2.Grigor\n3.Teemu\n4.Ben");
		int i = s.nextInt();
		String pass="";
		switch(i)
		{
			case(1):
				System.out.println("Julius valittu");
				pass = "juliusw";
				break;
			case(2):
				System.out.println("Grigor valittu");
				pass = "passwordi";
				break;
			case(3):
				System.out.println("Teemu valittu");
				pass = "teemu";
				break;
			case(4):
				System.out.println("Ben valittu");
				pass = "root";
				break;
		}

		//////////////////////////

		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn =DriverManager.getConnection("jdbc:mysql://localhost/varasto", "root", pass);
		} catch (SQLException e) {
			System.out.println("Yhteyden muodostaminen epäonnistui");
	    } catch (ClassNotFoundException e){
			System.out.println("JDBC-ajurin lataus epäonnistui");
		}

	}



	//-----METODIT-----//

	public static boolean LogIn(String uname, String pword)
	{
		boolean res = false; //Oletetaan, että login epäonnistuu

		PreparedStatement haetiedot=null;
		ResultSet rs = null;
		String pass="";

		try{
			//Parametrisoitu sql-kysely
			haetiedot = conn.prepareStatement("SELECT * FROM users WHERE user = ?");
			try {
				//Asetetaan argumentit sql-kyselyyn
				haetiedot.setString(1, uname);
				rs = haetiedot.executeQuery();//Hae annetulla käyttäjänimellä tietokanta rivi
				try {
					while(rs.next()){
						pass = rs.getString("pass"); //hae password column ja tallenna muuttujaan
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally{
					rs.close();
					System.out.println("Tulosjuokko suljettu");
				}
			}catch(SQLException e){
				System.out.println("Haku " + uname + " epäonnistui!");
				e.printStackTrace();
			}
		}catch(Exception e){
			System.out.println("Tietojen haku epäonnistui!");
		}finally{
			try {
				haetiedot.close();
				System.out.println("Haku kysely suljettu");
			} catch (SQLException e) {
				System.out.println("Yhteyden sulkemisessa vikaa");
				e.printStackTrace();
			} catch(Exception e){
				System.out.println("Vikaa LogInin lopussa");
			}
		}

		if(pass.equals(pword)){ //tarkistetaan salasanat
			//True vain jos funktioon tullut salasana
			//on sama mikä löytyy tietokannasta
			res = true;
		}
		return res;
	}

	public void close() throws SQLException
	{
		conn.close();
	}
}
