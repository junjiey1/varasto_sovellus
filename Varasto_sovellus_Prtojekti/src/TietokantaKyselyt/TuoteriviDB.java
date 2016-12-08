package TietokantaKyselyt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vPakkaus.DB_AccessObject;
import vPakkaus.Hyllypaikka;
import vPakkaus.Product;
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
  private DB_AccessObject db = null;

  public TuoteriviDB(Connection conn, ProductDB productdb, HyllyDB hyllydb, DB_AccessObject db) {
    this.conn = conn;
    this.hyllydb = hyllydb;
    this.productdb = productdb;
    this.db = db;
    // TODO Auto-generated constructor stub
  }

  /**
   * Haetaan kaikki tuoterivit hyllypaikan perusteella
   *
   * @param hyllynimi
   *          Hyllynpaikan tunnus (String)
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
        Tuotejoukko tuotejoukko = new Tuotejoukko(productdb.findProduct(nimet.get(i)),
            hyllydb.haeHylly(hyllynimi), määrät.get(i));
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
   * @param joukko Lisattava tuotejoukko (Tuotejoukko)
   *
   * @return Onnistuminen/epäonnistuminen (Boolean)
   */

  public boolean addProductToTuoteriviTable(Tuotejoukko joukko) {

    try {
      ps = conn.prepareStatement(
          "INSERT INTO tuoterivi(tuoteID, hyllypaikka, maara)" + "VALUES (?,?,?);");

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
   *          Tuotteen sijainti (String)
   *
   * @param nimi
   *          Tuotteen nimi (String)
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

    return maara;

  }

  public ArrayList<Tuotejoukko> haeTuotteenKaikkiTuoterivit(Product p) {
    ArrayList<Tuotejoukko> tjt = new ArrayList();
    ArrayList<String> hpt = new ArrayList();
    ArrayList<Integer> tuoteidt = new ArrayList();
    ArrayList<Integer> määrät = new ArrayList();

    try {
      ps = conn.prepareStatement(
          "SELECT tuoterivi.riviID, tuoterivi.tuoteID, tuoterivi.maara, tuoterivi.hyllypaikka FROM tuoterivi WHERE tuoterivi.tuoteID=?;");
      ps.setInt(1, p.getID());

      rs = ps.executeQuery();
      while (rs.next()) {
        int riviID = rs.getInt("riviID");
        int tuoteID = rs.getInt("tuoteID");
        int maara = rs.getInt("maara");
        String hp = rs.getString("hyllypaikka");
        tuoteidt.add(tuoteID);
        määrät.add(maara);
        hpt.add(hp);
      }

      for (int i = 0; i < tuoteidt.size(); i++) {
        Tuotejoukko tuotejoukko = new Tuotejoukko(productdb.findProductWithID(tuoteidt.get(i)),
            hyllydb.haeHylly(hpt.get(i)), määrät.get(i));
        tjt.add(tuotejoukko);
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


    return tjt;

  }

  /**
   * Muokataan tuoteriviä tietokannassa.
   *
   * @param tuotejoukko
   *          muokattava tuotejoukko (Tuotejoukko)
   *
   * @return Onnistuminen/epäonnistuminen (Boolean)
   */

  public boolean muokkaaTuoteriviä(Tuotejoukko tuotejoukko) {

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
      return false;
    }

    return true;
  }

  public boolean deleteTuoterivi(Tuotejoukko tj) {
    try {
      ps = conn.prepareStatement("DELETE FROM tuoterivi WHERE tuoterivi.tuoteID = ? AND tuoterivi.hyllypaikka = ?");
      ps.setInt(1, tj.getProduct().getID());
      ps.setString(2, tj.getHylly().getNimi());
      ps.executeUpdate();
      ps.close();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

}
