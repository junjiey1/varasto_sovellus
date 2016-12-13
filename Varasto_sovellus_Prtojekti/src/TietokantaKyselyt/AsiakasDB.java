package TietokantaKyselyt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vPakkaus.Asiakas;
import vPakkaus.DB_AccessObject;
import vPakkaus.Product;

/**
 * Luokka vastaa Asiakas tietokantataulun kyselyistä
 *
 */


public class AsiakasDB {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private DB_AccessObject db;

	public AsiakasDB(Connection conn, DB_AccessObject db) {
		this.conn = conn;
		this.db=db;
	}

	/**
	 * Lisataan uusi asiakas tietokantatauluun.
	 *
	 * @param a
	 * 		Asiakasolio (Asiakas)
	 *
	 * @return Onnistuminen/epäonnistuminen (boolean)
	 */

	public boolean addAsiakas(Asiakas a) {
		if (a == null)
			return false;
		try {
			ps = conn.prepareStatement(
					"INSERT INTO asiakas (nimi, osoite, postinumero, kaupunki, email, puhelinnumero) VALUES (?,?,?,?,?,?)");
			ps.setString(1, a.getNimi());
			ps.setString(2, a.getOsoit());
			ps.setString(3, a.getPosnumero());
			ps.setString(4, a.getKaupun());
			ps.setString(5, a.getEmai());
			ps.setString(6, a.getNumero());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Paivitetaan asiakkaan tiedot tietokantatauluun.
	 *
	 * @param a
	 * 		Asiakasolio (Asiakas)
	 *
	 * @return Onnistuminen/epäonnistuminen (boolean)
	 */

	public boolean updateAsiakas(Asiakas a) {
		if (a == null)
			return false;
		try {
			ps = conn.prepareStatement(
					"UPDATE asiakas SET nimi = ?, osoite = ?, postinumero = ?, kaupunki = ?, email = ?, puhelinnumero = ? WHERE asiakasnumero = ?");
			ps.setString(1, a.getNimi());
			ps.setString(2, a.getOsoit());
			ps.setString(3, a.getPosnumero());
			ps.setString(4, a.getKaupun());
			ps.setString(5, a.getEmai());
			ps.setString(6, a.getNumero());
			ps.setInt(7, a.getID());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			db.setErrorMsg(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Haetaan asiakaan tiedot tietokannasta.
	 *
	 * @param nimi
	 * 		Asiakkaan nimi (String)
	 *
	 * @return Asiakkaan tiedot (Asiakas)
	 */

	public Asiakas haeAsiakasNimella(String nimi) {
		Asiakas asiakas = null;
		try {
			ps = conn.prepareStatement("SELECT asiakasnumero, nimi,"
					+ "osoite, postinumero, kaupunki, email, puhelinnumero " + "FROM asiakas WHERE nimi = ?");
			ps.setString(1, nimi);
			asiakas = luoAsiakas();
		} catch (SQLException e) {
			e.printStackTrace();
			db.setErrorMsg(e.getMessage());
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
				db.setErrorMsg(e.getMessage());
			}
		}
		return asiakas;
	}

	public Asiakas haeAsiakasNumerolla(int ID){
	  Asiakas asiakas = null;
    try {
      ps = conn.prepareStatement("SELECT asiakasnumero, nimi,"
          + "osoite, postinumero, kaupunki, email, puhelinnumero " + "FROM asiakas WHERE asiakasnumero = ?");
      ps.setInt(1, ID);
      asiakas = luoAsiakas();
    } catch (SQLException e) {
      e.printStackTrace();
      db.setErrorMsg(e.getMessage());
    } finally {
      try {
        rs.close();
        ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
        db.setErrorMsg(e.getMessage());
      }
    }
    return asiakas;
	}

	private Asiakas luoAsiakas() throws SQLException{
	  rs = ps.executeQuery();
    while (rs.next()) {
      String name = rs.getString("nimi");
      String osoite = rs.getString("osoite");
      String post = rs.getString("postinumero");
      String kaupunki = rs.getString("kaupunki");
      String email = rs.getString("email");
      String puhelin = rs.getString("puhelinnumero");
      Asiakas asiakas = new Asiakas(name, osoite, kaupunki, email, puhelin, post);
      asiakas.setID(rs.getInt("asiakasnumero"));
      return asiakas;
    }
    return null;
	}

	/**
	 * Haetaan yhden tai useamman asiakkaan tiedot merkkijonon perusteella.
	 * Merkkijono voi olla osa asiakkaan nimestä, jolloin haetaan kaikki asiakkaat,
	 * joiden nimessä esiintyy kyseinen merkkijono
	 *
	 * @param nimi
	 * 		asiakkaan nimi (String)
	 *
	 * @return lista asiakkaista (ArrayList<Asiakas>)
	 */

	public ArrayList<Asiakas> haeAsiakkaat(String nimi) {
		ArrayList<Asiakas> asiakkaat = new ArrayList();
		Asiakas asiakas;

		try {
			nimi = "%" + nimi + "%";
			ps = conn.prepareStatement("SELECT asiakasnumero, nimi,"
					+ "osoite, postinumero, kaupunki, email, puhelinnumero " + "FROM asiakas WHERE nimi LIKE ?");

			ps.setString(1, nimi);
			rs = ps.executeQuery();
			while (rs.next()) {
				String name = rs.getString("nimi");
				String osoite = rs.getString("osoite");
				String post = rs.getString("postinumero");
				String kaupunki = rs.getString("kaupunki");
				String email = rs.getString("email");
				String puhelin = rs.getString("puhelinnumero");
				asiakas = new Asiakas(name, osoite, kaupunki, email, puhelin, post);
				asiakas.setID(rs.getInt("asiakasnumero"));
				asiakkaat.add(asiakas);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return asiakkaat;
	}

  public boolean deleteAsiakas(Asiakas a) {
    try {
      ps = conn.prepareStatement("DELETE FROM asiakas WHERE nimi = ?");
      ps.setString(1, a.getNimi());
      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

}
