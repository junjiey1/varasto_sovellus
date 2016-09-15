package vPakkaus;

import java.sql.Connection;
import java.sql.Date;
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

	public static int[] LogIn(String uname, String pword){
		int res = 0; //Oletetaan, että login epäonnistuu

		PreparedStatement haetiedot=null;
		ResultSet rs = null;
		String pass="";
		int id=0;

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
						id = rs.getInt("id");
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
			res = 1;
		}
		int[] list = {res, id};
		return list;
	}

	public static boolean Lisaa(String nimi, double paino, double tilavuus,
			String hyllypaikka, Date saapumispaiva, Date lahtopaiva, float hinta,
			int lisaaja_id, int maara)
	{
		PreparedStatement LisaaTuote=null;
		boolean error=false;
		try {
			LisaaTuote = conn.prepareStatement("INSERT INTO tuotteet(nimike, paino, tilavuus, hyllypaikka"
					+ ", saapumispaiva, lahtopaiva, hinta, lisaaja_id,  maara) "
					+ "VALUES (?,?,?,?,?,?,?,?,?);");
		} catch (SQLException e) {
			System.out.println("Lisäys epäonnistui!");
			error=true;
			e.printStackTrace();
		}
		 try {
			LisaaTuote.setString(1, nimi);
			LisaaTuote.setDouble(2, paino);
			LisaaTuote.setDouble(3, tilavuus);
			LisaaTuote.setString(4, hyllypaikka);
			LisaaTuote.setDate(5, saapumispaiva);
			LisaaTuote.setDate(6, lahtopaiva);
			LisaaTuote.setFloat(7, hinta);
			LisaaTuote.setInt(8, lisaaja_id);
			LisaaTuote.setInt(9, maara);
			LisaaTuote.executeUpdate();
			LisaaTuote.close();

		 } catch (SQLException e) {
			System.out.println("Lisäys epäonnistui!");
			error=true;
			e.printStackTrace();
		 }

		 if(!error){
			 return true;
		 }
		 return false;
	}

	public static <E> boolean PaivitaTietue(String taulukon_nimi, String tietueen_nimi,E kriteeri, E arvo)
	{
		boolean res = false;
		//kriteeri.getClass().
		return res;
	}

	public static void close() throws SQLException
	{
		conn.close();
	}


}
