package TietokantaKyselyt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vPakkaus.Hyllypaikka;
import vPakkaus.Product;

/**
 * Luokka vastaa hyllypaikka tietokantataulun kyselyistä.
 *
 */

public class HyllyDB {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public HyllyDB(Connection conn) {
		this.conn = conn;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Haetaan hylly hyllyn nimen perusteella.
	 *
	 * @param tunnus
	 * 		hyllynn nimi (String)
	 *
	 * @return hyllyolio (Hyllypaikka)
	 */

	public Hyllypaikka HaeHylly(String tunnus) {
		Hyllypaikka hyl = null;
		try {
			ps = conn.prepareStatement(
					"select hyllypaikka.tunnus , hyllypaikka.pituus, hyllypaikka.leveys, hyllypaikka.korkeus, hyllypaikka.lampotila, hyllypaikka.maksimi_paino from hyllypaikka where hyllypaikka.tunnus=?;");
			ps.setString(1, tunnus);
			rs = ps.executeQuery();
			while (rs.next()) {
				String nimi = rs.getString("tunnus");
				double leveys = rs.getDouble("leveys");
				double pituus = rs.getDouble("pituus");
				double syvyys = rs.getDouble("korkeus");
				int lampotila = rs.getInt("lampotila");
				double max_paino = rs.getDouble("maksimi_paino");
				hyl = new Hyllypaikka(nimi, pituus, leveys, syvyys, lampotila, max_paino);
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

		return hyl;
	}

	public ArrayList<Hyllypaikka> findHyllypaikka(String nimi) {
		ArrayList<Hyllypaikka> res = new ArrayList<Hyllypaikka>();

		return res;
	}

	/**
	 * Luodaan uusi hyllypaikka tietokantatauluun.
	 *
	 * @param hyllypaikka
	 * 		hyllypaikkaolio (Hyllypaikka)
	 *
	 * @return Onnistuminen/epäonnistuminen (Boolean)
	 */

	public boolean CreateHyllypaikka(Hyllypaikka hyllypaikka) {
		int varastoID = 513;

		try {
			ps = conn.prepareStatement(
					"INSERT INTO hyllypaikka(tunnus, pituus, leveys, korkeus, maksimi_paino, lampotila, varastoID) VALUES (?,?,?,?,?,?,?);");
			// haetaan tuotteen id tietokannasta

			ps.setString(1, hyllypaikka.getNimi());
			ps.setDouble(2, hyllypaikka.getPituus());
			ps.setDouble(3, hyllypaikka.getLeveys());
			ps.setDouble(4, hyllypaikka.getKorkeus());
			ps.setDouble(5, hyllypaikka.getMax_paino());
			ps.setDouble(6, hyllypaikka.getLämpötila());
			ps.setInt(7, varastoID);

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			System.out.println("Lisäys epäonnistui hyllypaikkataulukkoon!");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Heataan kaikki hyllypaikat, jossa tuotetta sijaitsee
	 *
	 * @param product
	 * 		product-olio (Product)
	 *
	 * @return Lista hyllypaikkojen nimistä (ArrayList<String>)
	 */

	public ArrayList<Hyllypaikka> HaeTuotteenHyllypaikat(Product product) {
		ArrayList<Hyllypaikka> tuotteen_hyllypaikat = new ArrayList();
		try {
			ps = conn.prepareStatement(
					"SELECT hyllypaikka.tunnus, hyllypaikka.pituus, hyllypaikka.leveys, hyllypaikka.korkeus, hyllypaikka.maksimi_paino, hyllypaikka.lampotila, hyllypaikka.varastoID from hyllypaikka, tuoterivi, tuote WHERE hyllypaikka.tunnus = tuoterivi.hyllypaikka AND tuoterivi.tuoteID = tuote.tuoteID AND tuote.nimi = ?;");
			ps.setString(1, product.getProduct_name());
			rs = ps.executeQuery();
			while (rs.next()) {
				String nimi = rs.getString("tunnus");
				double pituus = rs.getDouble("pituus");
				double leveys = rs.getDouble("leveys");
				double korkeus = rs.getDouble("korkeus");
				double max_paino = rs.getDouble("maksimi_paino");
				int lampotila = rs.getInt("lampotila");
				int varastoID = rs.getInt("varastoID");
				Hyllypaikka hp = new Hyllypaikka(nimi, pituus, leveys, korkeus, lampotila, max_paino);
				tuotteen_hyllypaikat.add(hp);
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

		return tuotteen_hyllypaikat;
	}

}
