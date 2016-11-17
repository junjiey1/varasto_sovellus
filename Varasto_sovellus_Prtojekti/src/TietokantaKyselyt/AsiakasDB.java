package TietokantaKyselyt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vPakkaus.Asiakas;

public class AsiakasDB {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public AsiakasDB(Connection conn) {
		// TODO Auto-generated constructor stub
		this.conn = conn;
	}

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
			return false;
		}
		return true;
	}

	public Asiakas haeAsiakas(String nimi) {
		Asiakas asiakas = null;
		try {
			ps = conn.prepareStatement("SELECT asiakasnumero, nimi,"
					+ "osoite, postinumero, kaupunki, email, puhelinnumero " + "FROM asiakas WHERE nimi = ?");
			ps.setString(1, nimi);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("asiakasnumero");
				String name = rs.getString("nimi");
				String osoite = rs.getString("osoite");
				String post = rs.getString("postinumero");
				String kaupunki = rs.getString("kaupunki");
				String email = rs.getString("email");
				String puhelin = rs.getString("puhelinnumero");
				asiakas = new Asiakas(name, osoite, kaupunki, email, puhelin, post);
				asiakas.setID(rs.getInt("asiakasnumero"));
			}
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return asiakas;
	}

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
				int id = rs.getInt("asiakasnumero");
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

}
