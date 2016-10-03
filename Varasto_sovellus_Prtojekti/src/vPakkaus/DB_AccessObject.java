package vPakkaus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DB_AccessObject {
	// ACCESS SQL_DB_OBJ.
	private static Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

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

		ArrayList<Boolean> errors = new ArrayList();
		Integer id = null;

		boolean löytyy = tarkistaLoytyykoTuote(nimi);
		//tuotetta ei löydy vielä tietokannasta lisätään uusi
		if (!löytyy) { //!löytyy tarkoittaa että tuotetta ei löydy ja voidaan lisätä uusi

			errors.add(lisaaTuote(nimi, hinta, paino, tilavuus));
			id = haeTuotteenIDNimella(nimi);
			System.out.println("id: "+id);
			if (id == null) {
				errors.add(false);
			}

			errors.add(lisaaTuoteidHyllypaikkaan(hyllypaikka, id));
			errors.add(lisaaTuoteidVarastoon(maara, id));

		} else {
			System.out.println("nimi löytyy jo tietokannasta, anna toinen nimi");
			errors.add(löytyy);
		}

		if (errors.contains(true)) {
		    System.out.println("Virhe tapahtunut lisäämisessä");
		} else {
		    System.out.println("Onnistunut lisääminen");
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

	public boolean lisaaTuote(String nimi, float hinta, double paino, double tilavuus) {
		boolean error = false;
		try {
			ps = conn.prepareStatement("INSERT INTO tuote(nimi, hinta, paino, tilavuus)" + "VALUES (?,?,?,?);");
			//haetaan tuotteen id tietokannasta
		}

		catch (SQLException e) {

			System.out.println("Lisäys epäonnistui!");
			error = true;
			e.printStackTrace();

		}
		try {
			ps.setString(1, nimi);
			ps.setFloat(2, hinta);
			ps.setDouble(3, paino);
			ps.setDouble(4, tilavuus);

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			System.out.println("Lisäys epäonnistui!");
			error = true;
			e.printStackTrace();
		}
		return error;
	}

	public boolean tarkistaLoytyykoTuote(String nimi) {
		boolean error = false;

		try {
			ps = conn.prepareStatement("SELECT tuoteID FROM tuote WHERE nimi = ?"); //haetaan tuote ja tarkistetaan löytyykö tuote jo nimellä tietokannasta

			// Asetetaan yksi argumentti nimi
			ps.setString(1, nimi);
			rs = ps.executeQuery();// Hae annetulla tuotteen nimellä
			// tietokanta rivi

			if (!rs.isBeforeFirst()) { // jos tuotetta ei löydy voidaan lisätä uusi tietokantaab
				// uusi
				System.out.println("Tuotetta ei löydy kyseisellä nimellä, voidaan lisätä uusi");
			}

		} catch (SQLException e) {
			System.out.println("Lisäys epäonnistui!");
			e.printStackTrace();
			error = true;
		}
		return error;
	}

	public int haeTuotteenIDNimella(String nimi) {
		Integer id = null;

		try {
			ps = conn.prepareStatement("SELECT tuoteID FROM tuote WHERE nimi = ?");

			// Asetetaan argumentit sql-kyselyyn
			ps.setString(1, nimi);
			rs = ps.executeQuery();// Hae annetulla käyttäjänimellä
			// tietokanta rivi

			while (rs.next()) {
				id = rs.getInt("tuoteID");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public boolean lisaaTuoteidHyllypaikkaan(String hyllypaikka, int id){
		boolean error = false;

		try {
			ps = conn.prepareStatement("INSERT INTO hyllypaikka(tunnus, tuoteID)" + "VALUES (?,?);");
		}

		catch (SQLException e) {

			System.out.println("Lisäys epäonnistui!");
			error = true;
			e.printStackTrace();
		}

		try {
			ps.setString(1, hyllypaikka);
			ps.setInt(2, id);

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			System.out.println("Lisäys epäonnistui!");
			e.printStackTrace();
			error = true;
		}
		return error;
	}

	public boolean lisaaTuoteidVarastoon(int maara, int id) {
		boolean error = false;
		try {
			ps= conn.prepareStatement("INSERT INTO varasto(varastoID, maara, tuoteID)" + "VALUES (?,?,?);");
		}

		catch (SQLException e) {

			System.out.println("Lisäys epäonnistui!");
			error = true;
			e.printStackTrace();

		}
		try {
			int varastoID = 1;
			ps.setInt(1, varastoID);
			ps.setInt(2, maara);
			ps.setInt(3, id);

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			System.out.println("Lisäys epäonnistui!");
			e.printStackTrace();
			error = true;
		}
		return error;
	}

	public static void close() throws SQLException {
		conn.close();
	}

}
