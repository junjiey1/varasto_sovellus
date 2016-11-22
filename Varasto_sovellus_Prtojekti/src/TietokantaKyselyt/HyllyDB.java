package TietokantaKyselyt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vPakkaus.DB_AccessObject;
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
	private DB_AccessObject db;

	public HyllyDB(Connection conn, DB_AccessObject db) {
		this.conn = conn;
		this.db = db;
	}

	/**
	 * Haetaan hylly hyllyn nimen perusteella.
	 *
	 * @param tunnus
	 * 		hyllynn nimi (String)
	 *
	 * @return hyllyolio (Hyllypaikka)
	 */

	public Hyllypaikka haeHylly(String tunnus) {
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
			db.setErrorMsg(e.getMessage());
		} finally {
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				db.setErrorMsg(e.getMessage());
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

	public boolean createHyllypaikka(Hyllypaikka hyllypaikka) {
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
			db.setErrorMsg(e.getMessage());
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

	public ArrayList<String> haeTuotteenHyllypaikat(Product product) {
		ArrayList<String> tuotteen_hyllypaikat = new ArrayList<String>();
		try {
			ps = conn.prepareStatement(
					"SELECT tuoterivi.hyllypaikka FROM tuoterivi, tuote WHERE tuoterivi.tuoteID = tuote.tuoteID AND tuote.nimi = ?;");
			ps.setString(1, product.getProduct_name());
			rs = ps.executeQuery();
			while (rs.next()) {
				String hp_nimi = rs.getString("nimi");

				tuotteen_hyllypaikat.add(hp_nimi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			db.setErrorMsg(e.getMessage());
		} finally {
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
			  db.setErrorMsg(e.getMessage());
				e.printStackTrace();
			}
		}
		return tuotteen_hyllypaikat;
	}

	public boolean paivitaHyllynTiedot(Hyllypaikka h){
	   try {
	      ps = conn.prepareStatement("UPDATE hyllypaikka SET pituus = ?, leveys = ?, korkeus = ?, maksimi_paino = ?, lampotila = ? WHERE tunnus = ? ");
        ps.setDouble(1, h.getLeveys());
        ps.setDouble(2, h.getLeveys());
        ps.setDouble(3, h.getKorkeus());
        ps.setDouble(4, h.getMax_paino());
        ps.setInt(5, h.getLämpötila());
        ps.setString(6, h.getNimi());
	      ps.executeUpdate();
	      ps.close();
	    } catch (SQLException e) {
	      e.printStackTrace();
	      db.setErrorMsg(e.getMessage());
	      try {
          ps.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }
	      return false;
	    }
	   return true;
	}

}
