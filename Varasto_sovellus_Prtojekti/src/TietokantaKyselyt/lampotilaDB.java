package TietokantaKyselyt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vPakkaus.Product;
import vPakkaus.Tuotejoukko;

/**
 * Luokka vastaa lampotilataulun tietokantakyselyista
 *
 */

public class lampotilaDB {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public lampotilaDB(Connection conn) {
		this.conn = conn;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Lisataan tuotteelle lampotila
	 *
	 * @param product
	 * 		product-olio, jolle lisataan lampotila (Product)
	 *
	 * @return Onnistuminen/epäonnistuminen (Boolean)
	 */

	public boolean addTemperatures(Product product) {
		// Tarvitaan tuotteen ID jotta voidaan lisätä arvoja Lämpötila
		// taulukkoon
		System.out.println("ID " + product.getID());
		try {
			ps = conn.prepareStatement("INSERT INTO lampotila(tuoteID, lampotila_max, lampotila_min) VALUES (?,?,?);");
			// haetaan tuotteen id tietokannasta

			ps.setInt(1, product.getID());
			ps.setInt(2, product.getMax_temperature());
			ps.setInt(3, product.getMin_temperature());

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			System.out.println("Lisäys epäonnistui lampotilataulukkoon!");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean checkLampotila(Tuotejoukko joukko) {
		boolean lampotila = true;
		if (joukko.getProduct().getMax_temperature() != null && joukko.getProduct().getMin_temperature() != null) {
			if (joukko.getHylly().getLämpötila() <= joukko.getProduct().getMax_temperature()
					&& joukko.getHylly().getLämpötila() >= joukko.getProduct().getMin_temperature()) {
				System.out.println("Tuotteen lämpötila on sopiva hyllypaikkaan");
			} else {
				System.out.println("Tuotteen lämpötila vaatimus ei vastaa hyllyn lämpötilaa");
				lampotila = false;
			}
		} else {
			System.out.println("tuotteelle ei ole asetettu lämpötila rajoituksia");
		}
		return lampotila;
	}

	/**
	 * Hakee tuotteen minimi- ja maksimilampotilan.
	 *
	 * @param tuotejoukko
	 * 		tuote (Product)
	 *
	 * @return tuote lampotilojen kanssa (Product)
	 */
	public Product findTemperatures(Product pro) {
		if (pro.getTemp()) {
			try {
				ps = conn.prepareStatement("SELECT lampotila_max, lampotila_min FROM lampotila WHERE tuoteID = ?");
				// Asetetaan argumentit sql-kyselyyn
				ps.setInt(1, pro.getID());
				rs = ps.executeQuery();
				while (rs.next()) {
					int lampotila_max = rs.getInt("lampotila_max");
					int lampotila_min = rs.getInt("lampotila_min");
					pro.setMax_temperature(lampotila_max);
					pro.setMin_temperature(lampotila_min);
				}
			} catch (SQLException e) {
				System.out.println("Ei voitu hakea lämpötilaa!");
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
		}
		return pro;
	}

	/**
	 * Tarkistaa jos tuotteelle on asetettu lampotilarajoituksia
	 *
	 * @param id
	 * 		tuotteen id (int)
	 *
	 * @return Loytyy/ei loydy (Boolean)
	 */

	public boolean checkIfTuoteIDExcistInLampoTila(int ID) {
		int res = 0;
		try {
			ps = conn.prepareStatement("SELECT 1 AS total FROM lampotila WHERE tuoteID = ?;");
			ps.setInt(1, ID);
			rs = ps.executeQuery();
			while (rs.next())
				res = rs.getInt("total");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("RES " + res);
		if (res == 1)
			return true;
		return false;
	}

	public boolean deleteLampotila(Product p) {
		try {
			ps = conn.prepareStatement("UPDATE tuote SET lampotila_boolean = ? WHERE tuoteID = ?");
			ps.setInt(1, 0);
			ps.setInt(2, p.getID());
			ps.executeUpdate();
			ps.close();
			ps = conn.prepareStatement("DELETE FROM lampotila WHERE tuoteID = ?");
			ps.setInt(1, p.getID());
			ps.executeUpdate();
			ps.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateLampotila(Product p) {
		try {
			if (!checkIfTuoteIDExcistInLampoTila(p.getID())) {
				System.out.println("set");
				ps = conn.prepareStatement("UPDATE tuote SET lampotila_boolean = ? WHERE tuoteID = ?");
				ps.setInt(1, 1);
				ps.setInt(2, p.getID());
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement(
						"INSERT INTO lampotila (lampotila_max, lampotila_min, tuoteID) VALUES (?,?,?)");
				ps.setInt(1, p.getMax_temperature());
				ps.setInt(2, p.getMin_temperature());
				ps.setInt(3, p.getID());
				ps.executeUpdate();
			} else {
				System.out.println("upd");
				ps = conn.prepareStatement(
						"UPDATE lampotila SET lampotila_max = ?, lampotila_min = ? WHERE tuoteID = ?");
				ps.setInt(1, p.getMax_temperature());
				ps.setInt(2, p.getMin_temperature());
				ps.setInt(3, p.getID());
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
