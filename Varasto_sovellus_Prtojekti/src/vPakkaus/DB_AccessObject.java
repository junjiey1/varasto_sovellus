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
		/////////////////T�m� osio vain testailua varten
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
			System.out.println("Yhteyden muodostaminen ep�onnistui");
	    } catch (ClassNotFoundException e){
			System.out.println("JDBC-ajurin lataus ep�onnistui");
		}

	}



	//-----METODIT-----//

	public static boolean LogIn(String uname, String pword)
	{
		boolean res = false; //Oletetaan, ett� login ep�onnistuu

		PreparedStatement haetiedot=null;
		ResultSet rs = null;
		String pass="";

		try{
			//Parametrisoitu sql-kysely
			haetiedot = conn.prepareStatement("SELECT * FROM users WHERE user = ?");
			try {
				//Asetetaan argumentit sql-kyselyyn
				haetiedot.setString(1, uname);
				rs = haetiedot.executeQuery();//Hae annetulla k�ytt�j�nimell� tietokanta rivi
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
				System.out.println("Haku " + uname + " ep�onnistui!");
				e.printStackTrace();
			}
		}catch(Exception e){
			System.out.println("Tietojen haku ep�onnistui!");
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
			//on sama mik� l�ytyy tietokannasta
			res = true;
		}
		return res;
	}

	public void close() throws SQLException
	{
		conn.close();
	}
}
