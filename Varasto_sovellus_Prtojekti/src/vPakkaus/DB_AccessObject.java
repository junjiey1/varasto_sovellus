package vPakkaus;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Scanner;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class DB_AccessObject {
	// ACCESS SQL_DB_OBJ.
	private static Connection conn = null;

	public DB_AccessObject() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:9000/varasto", "toimi", "toimi");
		} catch (SQLException e) {
			System.out.println("Yhteyden muodostaminen epäonnistui");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC-ajurin lataus epäonnistui");
		}

	}

	// -----METODIT-----//

	public int[] LogIn(String uname, String pword) {
		int res = 0; // Oletetaan, että login epäonnistuu

		PreparedStatement haetiedot = null;
		ResultSet rs = null;
		String pass = "";
		int id = 0;

		try {
			// Parametrisoitu sql-kysely
			haetiedot = conn.prepareStatement("SELECT * FROM users WHERE user = ?");
			try {
				// Asetetaan argumentit sql-kyselyyn
				haetiedot.setString(1, uname);
				rs = haetiedot.executeQuery();// Hae annetulla käyttäjänimellä
				// tietokanta rivi
				try {
					while (rs.next()) {
						pass = rs.getString("pass"); // hae password column ja
						// tallenna muuttujaan
						id = rs.getInt("id");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					rs.close();
					System.out.println("Tulosjuokko suljettu");
				}
			} catch (SQLException e) {
				System.out.println("Haku " + uname + " epäonnistui!");
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("Tietojen haku epäonnistui!");
		} finally {
			try {
				haetiedot.close();
				System.out.println("Haku kysely suljettu");
			} catch (SQLException e) {
				System.out.println("Yhteyden sulkemisessa vikaa");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Vikaa LogInin lopussa");
			}
		}

		if (pass.equals(pword)) { // tarkistetaan salasanat
			// True vain jos funktioon tullut salasana
			// on sama mikä löytyy tietokannasta
			res = 1;
		}
		int[] list = { res, id };
		return list;
	}

	public boolean Lisaa(String nimi, double paino, double tilavuus, String hyllypaikka, float hinta, int maara) {

		PreparedStatement haeID = null;
		PreparedStatement LisaaTuote = null;

		ResultSet rs = null;

		boolean löytyy = false;
		boolean error = false;

		Integer id = null;

		try {
			haeID = conn.prepareStatement("SELECT tuoteID FROM tuote WHERE nimi = ?");

			// Asetetaan argumentit sql-kyselyyn
			haeID.setString(1, nimi);
			rs = haeID.executeQuery();// Hae annetulla käyttäjänimellä
										// tietokanta rivi

			if (!rs.isBeforeFirst()) { // jos tuotettta ei löydy voidaan lisätä
										// uusi
				System.out.println("Tuotetta ei löydy, voidaan lisätä uusi");
				löytyy = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (löytyy) {

			try {
				LisaaTuote = conn
						.prepareStatement("INSERT INTO tuote(nimi, hinta, paino, tilavuus)" + "VALUES (?,?,?,?);");

			}

			catch (SQLException e) {

				System.out.println("Lisäys epäonnistui!");
				error = true;
				e.printStackTrace();

			}
			try {
				LisaaTuote.setString(1, nimi);
				LisaaTuote.setFloat(2, hinta);
				LisaaTuote.setDouble(3, paino);
				LisaaTuote.setDouble(4, tilavuus);

				LisaaTuote.executeUpdate();
				LisaaTuote.close();

			} catch (SQLException e) {
				System.out.println("Lisäys epäonnistui!");
				error = true;
				e.printStackTrace();
			}
		}

		if (!error) {
			return true;
		}
		return false;
	}

	public static <E> boolean PaivitaTietueByID(String taulukon_nimi, int id, E[] arvot, int[] tietueet) {
		boolean error = false;
		for (int i = 0; i < tietueet.length; i++) {

		}
		return error;
	}

	public static void close() throws SQLException {
		conn.close();
	}

}
