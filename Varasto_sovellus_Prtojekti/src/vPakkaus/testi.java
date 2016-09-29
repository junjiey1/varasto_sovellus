package vPakkaus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class testi {

	public static void main(String[] args) {
		Connection conn = null;
		try{//toimi, toimi
			Class.forName("com.mysql.jdbc.Driver");
			conn =DriverManager.getConnection("jdbc:mysql://localhost:9000/test", "toimi", "toimi");
			System.out.println(conn.getNetworkTimeout());
		} catch (SQLException e) {
			do {
				System.err.println("Viesti: " + e.getMessage());
				System.err.println("Virhekoodi: " + e.getErrorCode());
				System.err.println("SQL-tilakoodi: " + e.getSQLState());
			} while (e.getNextException() != null);

	    } catch (ClassNotFoundException e){
			System.out.println("JDBC-ajurin lataus epäonnistui");
		}

		PreparedStatement haetiedot=null;
		ResultSet rs = null;
		String pass="";

		try{
			//Parametrisoitu sql-kysely
			haetiedot = conn.prepareStatement("SELECT * FROM testi");
			try {
				//Asetetaan argumentit sql-kyselyyn
				rs = haetiedot.executeQuery();//Hae annetulla käyttäjänimellä tietokanta rivi
				try {
					while(rs.next()){
						pass = rs.getString("nimi"); //hae password column ja tallenna muuttujaan
						System.out.println(pass);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}catch(SQLException e){
				//System.out.println("Haku " + uname + " epäonnistui!");
				e.printStackTrace();
			}
		}catch(Exception e){
			System.out.println("Tietojen haku epäonnistui!");
		}

	}
}
