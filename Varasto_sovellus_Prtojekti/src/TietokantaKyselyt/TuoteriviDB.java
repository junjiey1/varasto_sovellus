package TietokantaKyselyt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vPakkaus.Tuotejoukko;

/**
 * Luokka vastaa tuoterivi tietokantataulun kyselyistä.
 *
 */

public class TuoteriviDB {

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private ProductDB productdb = null;
	private HyllyDB hyllydb = null;

	public TuoteriviDB(Connection conn, ProductDB productdb, HyllyDB hyllydb) {
		this.conn = conn;
		this.hyllydb = hyllydb;
		this.productdb = productdb;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Haetaan kaikki tuoterivit hyllypaikan perusteella
	 *
	 * @param hyllynimi
	 * 		Hyllynpaikan tunnus (String)
	 *
	 * @return tuotejoukkojen lista (ArrayList<Tuotejoukko>)
	 */

	public ArrayList<Tuotejoukko> haeHyllynTuotejoukot(String hyllynimi) {

		ArrayList<Tuotejoukko> tj = new ArrayList();
		ArrayList<String> nimet = new ArrayList();
		ArrayList<Integer> määrät = new ArrayList();

		try {
			ps = conn.prepareStatement(
					"SELECT tuote.nimi, tuoterivi.maara FROM tuote, tuoterivi WHERE tuote.tuoteID = tuoterivi.tuoteID AND tuoterivi.hyllypaikka = ?;");

			// Asetetaan argumentit sql-kyselyyn
			ps.setString(1, hyllynimi);
			rs = ps.executeQuery();// Hae annetulla käyttäjänimellä
			// tietokanta rivi

			while (rs.next()) {
				int maara = rs.getInt("maara");
				String nimi = rs.getString("nimi");
				nimet.add(nimi);
				määrät.add(maara);
			}

			for (int i = 0; i < nimet.size(); i++) {
				Tuotejoukko tuotejoukko = new Tuotejoukko(productdb.findProduct(nimet.get(i)), hyllydb.HaeHylly(hyllynimi),
						määrät.get(i));
				tj.add(tuotejoukko);
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
		return tj;
	}

	/*
	 * Lisataan tuotejoukko tietokantaan
	 *
	 * @param joukko
	 * 		Lisattava tuotejoukko (Tuotejoukko)
	 *
	 * @return Onnistuminen/epäonnistuminen (Boolean)
	 */

	public boolean addProductToTuoteriviTable(Tuotejoukko joukko) {

		try {
			ps = conn.prepareStatement("INSERT INTO tuoterivi(tuoteID, hyllypaikka, maara)" + "VALUES (?,?,?);");

			ps.setInt(1, joukko.getProduct().getID());
			ps.setString(2, joukko.getHylly().getNimi());
			ps.setInt(3, joukko.getMaara());

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			System.out.println("Lisäys epäonnistui tuoterivitaulukkoon!");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Haetaan tuotteen määrä tietystä hyllypaikasta
	 *
	 * @param hyllypaikka
	 * 		Tuotteen sijainti (String)
	 *
	 * @param nimi
	 * 		Tuotteen nimi (String)
	 *
	 * @return tuotteiden maara (int)
	 */

	public int tuotteidenMaaraHyllyssa(String nimi, String hyllypaikka) {
		int maara = 0;
		try {
			ps = conn.prepareStatement(
					"Select tuoterivi.maara FROM tuoterivi, tuote WHERE tuoterivi.tuoteID = tuote.tuoteID AND tuote.nimi = ? and  tuoterivi.hyllypaikka = ?;");
			ps.setString(1, nimi);
			ps.setString(2, hyllypaikka);
			rs = ps.executeQuery();
			while (rs.next()) {
				maara = rs.getInt("maara");
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

		return maara;

	}

	/**
	 * Muokataan tuoteriviä tietokannassa.
	 *
	 * @param tuotejoukko
	 * 		muokattava tuotejoukko (Tuotejoukko)
	 *
	 * @return Onnistuminen/epäonnistuminen (Boolean)
	 */

	public boolean MuokkaaTuoteriviä(Tuotejoukko tuotejoukko) {
		boolean error = false;

		try {
			ps = conn.prepareStatement(
					"UPDATE tuote, tuoterivi SET tuoterivi.maara = ?  WHERE tuote.tuoteID = tuoterivi.tuoteID AND tuote.nimi = ? AND tuoterivi.hyllypaikka = ?;");

			ps.setInt(1, tuotejoukko.getMaara());
			ps.setString(2, tuotejoukko.getProduct().getProduct_name());
			ps.setString(3, tuotejoukko.getHylly().getNimi());

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
			error = true;
		}

		return error;
	}

}
