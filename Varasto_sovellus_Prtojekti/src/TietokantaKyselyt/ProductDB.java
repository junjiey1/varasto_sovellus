package TietokantaKyselyt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vPakkaus.Product;

public class ProductDB {

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private lampotilaDB lampotiladb;

	public ProductDB(Connection conn, lampotilaDB lampotiladb) {
		this.conn = conn;
		this.lampotiladb = lampotiladb;
		// TODO Auto-generated constructor stub
	}

	public boolean updateProducts(ArrayList<Product> products) {
		boolean error = true;

		for (Product p : products) {

			try {
				System.out.println(p.toString());
				ps = conn.prepareStatement(
						"UPDATE tuote  SET" + " tuote.nimi = ?,tuote.hinta = ?, tuote.paino = ?, tuote.leveys = ?,"
								+ " tuote.pituus = ?, tuote.korkeus = ? WHERE tuote.tuoteID = ?");

				ps.setString(1, p.getProduct_name());
				ps.setFloat(2, p.getProduct_price());
				ps.setDouble(3, p.getProduct_weight());
				ps.setDouble(4, p.getProduct_width());
				ps.setDouble(5, p.getProduct_length());
				ps.setDouble(6, p.getProduct_height());
				ps.setInt(7, p.getID());

				ps.executeUpdate();
				ps.close();

				if (p.getTemp()) {
					if (!lampotiladb.updateLampotila(p))
						return false;
				} else {
					System.out.println("1");
					if (lampotiladb.checkIfTuoteIDExcistInLampoTila(p.getID())) {
						System.out.println("1");
						lampotiladb.deleteLampotila(p);
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
				error = false;
			}

		}

		return error;
	}

	public ArrayList<Product> findProducts(String nimi) {
		ArrayList<Product> products = new ArrayList();
		Product product;

		try {
			nimi = "%" + nimi + "%";
			ps = conn.prepareStatement("SELECT tuote.tuoteID, tuote.nimi,"
					+ "tuote.hinta, tuote.paino, tuote.pituus, tuote.leveys, tuote.korkeus, tuote.lampotila_boolean "
					+ "FROM tuote WHERE tuote.nimi LIKE ?");
					// + "AND tuote.tuoteID = hyllypaikka.tuoteID AND
					// tuote.tuoteID ="
					// + "varasto.tuoteID;");

			// Asetetaan argumentit sql-kyselyyn
			ps.setString(1, nimi);
			rs = ps.executeQuery();
			// tietokanta rivi

			while (rs.next()) {
				int id = rs.getInt("tuoteID");
				String name = rs.getString("nimi");
				double paino = rs.getDouble("paino");
				double pituus = rs.getDouble("pituus");
				double leveys = rs.getDouble("leveys");
				double korkeus = rs.getDouble("korkeus");
				float hinta = rs.getFloat("hinta");
				product = new Product(name, paino, leveys, korkeus, pituus, hinta);
				product.setID(id);
				if (rs.getInt("lampotila_boolean") == 1) {
					product.setTemp(true);
				}
				products.add(product);
			}
			for (Product p : products)
				if (p.getTemp())
					product = lampotiladb.findTemperatures(p);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return products;
	}

	public boolean addProductToTuoteTable(Product product) {
		int lampotila_boolean = 0;
		if (product.getMax_temperature() != null && product.getMin_temperature() != null)
			lampotila_boolean = 1;
		try {
			ps = conn.prepareStatement(
					"INSERT INTO tuote(nimi, hinta, paino, pituus, leveys, korkeus, lampotila_boolean)"
							+ "VALUES (?,?,?,?,?,?,?);");
			// haetaan tuotteen id tietokannasta

			ps.setString(1, product.getProduct_name());
			ps.setFloat(2, product.getProduct_price());
			ps.setDouble(3, product.getProduct_weight());
			ps.setDouble(4, product.getProduct_length());
			ps.setDouble(5, product.getProduct_width());
			ps.setDouble(6, product.getProduct_height());
			ps.setDouble(7, lampotila_boolean);

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			System.out.println("Lisäys epäonnistui tuotetaulukkoon!");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ArrayList<String> HaeHyllypaikanTuotteet(String hyllypaikka) {
		ArrayList<String> HP_Tuotteet = new ArrayList();
		try {
			ps = conn.prepareStatement(
					"Select tuote.nimi from tuote, tuoterivi WHERE tuoterivi.tuoteID = tuote.tuoteID AND tuoterivi.hyllypaikka = ?;");
			ps.setString(1, hyllypaikka);
			rs = ps.executeQuery();
			while (rs.next()) {
				String nimi = rs.getString("nimi");

				HP_Tuotteet.add(nimi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return HP_Tuotteet;
	}

	public Product findProduct(String nimi) {
		Product product = null;
		try {
			ps = conn.prepareStatement(
					"SELECT tuote.tuoteID, tuote.nimi, tuote.hinta, tuote.paino, tuote.pituus, tuote.leveys, tuote.korkeus, tuote.lampotila_boolean FROM tuote WHERE tuote.nimi = ?");

			// Asetetaan argumentit sql-kyselyyn
			ps.setString(1, nimi);
			rs = ps.executeQuery();// Hae annetulla käyttäjänimellä
			// tietokanta rivi

			while (rs.next()) {
				int id = rs.getInt("tuoteID");
				String name = rs.getString("nimi");
				double paino = rs.getDouble("paino");
				double pituus = rs.getDouble("pituus");
				double leveys = rs.getDouble("leveys");
				double korkeus = rs.getDouble("korkeus");
				float hinta = rs.getFloat("hinta");
				float lampotila_boolean = rs.getFloat("lampotila_boolean");
				product = new Product(name, paino, leveys, korkeus, pituus, hinta);
				product.setID(id);
				if (lampotila_boolean == 1)
					product.setTemp(true);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return product;
	}

}