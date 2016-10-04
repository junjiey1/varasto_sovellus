package vPakkaus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

		ArrayList<Boolean> errors = new ArrayList(); //virheet kerätään listaan, false = ei virhettä
		Integer id = null;
		Product product = findProduct(nimi); //tarkistetaan löytyykö tuotetta jo samalla nimellä

		if (product == null) { //!löytyy tarkoittaa että tuotetta ei löydy ja voidaan lisätä uusi

			addProductToDB(nimi, hinta, paino, tilavuus); // lisätään tuote tuotetaulukkoon
			id = getProductID(nimi);
			System.out.println("id = "+id);

			addProductLocation(hyllypaikka, id);
			addProductToWarehouse(maara, id);

		} else {
			System.out.println("Tuotetta ei voida lisätä tällä nimellä, tuote löytyy jo "+product);
		}

		if (errors.contains(true)) {
		    System.out.println("Virhe tapahtunut Prosessissa");
		} else {
		    System.out.println(nimi+" lisätty onnistuneesti");
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

	public boolean addProductToDB(String nimi, float hinta, double paino, double tilavuus) {

		boolean error = false;

		try {
			ps = conn.prepareStatement("INSERT INTO tuote(nimi, hinta, paino, tilavuus)" + "VALUES (?,?,?,?);");
			//haetaan tuotteen id tietokannasta

			ps.setString(1, nimi);
			ps.setFloat(2, hinta);
			ps.setDouble(3, paino);
			ps.setDouble(4, tilavuus);

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			error = true;
			System.out.println("Lisäys epäonnistui tuotetaulukkoon!");
			e.printStackTrace();
		}
		return error;
	}

	public int getProductID(String nimi) {
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

	public Product findProduct(String nimi) {
		Product product = null;

		try {
			ps = conn.prepareStatement("SELECT tuote.tuoteID, tuote.nimi, tuote.hinta, tuote.paino, tuote.tilavuus, hyllypaikka.tunnus FROM tuote, hyllypaikka WHERE tuote.nimi = ? AND tuote.tuoteID = hyllypaikka.tuoteID");

			// Asetetaan argumentit sql-kyselyyn
			ps.setString(1, nimi);
			rs = ps.executeQuery();// Hae annetulla käyttäjänimellä
			// tietokanta rivi

			while (rs.next()) {

				String name = rs.getString("nimi");
				String hyllypaikka = rs.getString("tunnus");
				double paino = rs.getDouble("paino");
				double tilavuus = rs.getDouble("tilavuus");
				float hinta = rs.getFloat("hinta");
				System.out.println(name+ " "+ hyllypaikka+ " "+ paino+ " "+ tilavuus+ " "+ hinta);

				product = new Product(name, hyllypaikka, paino, tilavuus, hinta);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return product;
	}

	public boolean addProductLocation(String hyllypaikka, int id){
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

	public boolean addProductToWarehouse(int maara, int id) {
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

//	public boolean updateProduct() {
//		boolean error = false;
//		Statement stmt = null;
//		try {
//			stmt = myCon.createStatement();
//			String sql = "Update valuutta set vaihtokurssi = "+valuutta.getVaihtokurssi()+" WHERE tunnus = '"+valuutta.getTunnus()+"' AND nimi = '"+valuutta.getNimi()+"'";
//			int palautus = stmt.executeUpdate(sql);
//
//			if (palautus == 0) {
//				System.out.println("Tietuetta ei löytynyt");
//			}
//		} catch (SQLException se) {
//			bool = false;
//			System.out.println("Virhe tapahtui, päivitystä ei tehty");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return bool;
//	}

	public static void close() throws SQLException {
		conn.close();
	}

}
