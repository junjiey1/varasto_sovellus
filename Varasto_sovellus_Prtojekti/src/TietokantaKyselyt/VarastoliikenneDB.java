package TietokantaKyselyt;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vPakkaus.Asiakas;
import vPakkaus.DB_AccessObject;
import vPakkaus.Product;
import vPakkaus.Varastoliikenne;

public class VarastoliikenneDB {

  private Connection conn = null;
  private PreparedStatement ps = null;
  private ResultSet rs = null;
  private AsiakasDB asiakasdb = null;
  private UsersDB usersdb = null;
  private DB_AccessObject db = null;

  public VarastoliikenneDB(Connection conn, AsiakasDB asiakasdb, UsersDB usersdb,
      DB_AccessObject db) {
    this.conn = conn;
    this.usersdb = usersdb;
    this.asiakasdb = asiakasdb;
    this.db = db;
    // TODO Auto-generated constructor stub
  }

  public boolean createVarastoliikenne(Varastoliikenne vl) {
    try {
      ps = conn.prepareStatement(
          "INSERT INTO varastoliikenne(tyyppi, pvm, toimitusosoite, tyontekijaID, asiakasnumero)"
              + "VALUES (?,?,?,?,?);");
      // haetaan tuotteen id tietokannasta

      ps.setInt(1, vl.getTyyppi());
      ps.setDate(2, vl.getPvm());
      ps.setString(3, vl.getOsoite());
      ps.setInt(4, vl.getUserID());
      ps.setInt(5, vl.getAsiaksID());

      ps.executeUpdate();
      ps.close();

    } catch (SQLException e) {
      System.out.println("Lisäys epäonnistui varastoliikennetaulukkoon!");
      db.setErrorMsg(e.getMessage());
      System.out.println(e.getMessage());
      // e.printStackTrace();
      return false;
    }
    return true;
  }

  public Varastoliikenne findVarastoliikenne(int varastoliikenneid) {
    Varastoliikenne vl = null;
    try {
      ps = conn.prepareStatement(
          "select varastoliikenne.varastoliikenneID, varastoliikenne.tyyppi, varastoliikenne.pvm, varastoliikenne.toimitusosoite, varastoliikenne.tyontekijaID, varastoliikenne.asiakasnumero from varastoliikenne where varastoliikenne.varastoliikenneID = ?;");

      // Asetetaan argumentit sql-kyselyyn
      ps.setInt(1, varastoliikenneid);
      rs = ps.executeQuery();// Hae annetulla käyttäjänimellä
      // tietokanta rivi

      while (rs.next()) {
        int id = rs.getInt("varastoliikenneID");
        int tyyppi = rs.getInt("tyyppi");
        Date pvm = rs.getDate("pvm");
        String osoite = rs.getString("toimitusosoite");
        int userid = rs.getInt("tyontekijaID");
        int asiakasid = rs.getInt("asiakasnumero");
        vl = new Varastoliikenne(tyyppi, pvm, osoite, userid, asiakasid, id);
        vl.setVarastoliikenneID(id);
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
    return vl;
  }

  public List<Varastoliikenne> findVarastoliikenneRivit(int customerID) {
    List<Varastoliikenne> res = new ArrayList<Varastoliikenne>();
    try {
      Asiakas asiakas = db.haeAsiakas(customerID);
      ps = conn.prepareStatement(
          "select varastoliikenne.varastoliikenneID, varastoliikenne.tyyppi, varastoliikenne.pvm, varastoliikenne.toimitusosoite, varastoliikenne.tyontekijaID, varastoliikenne.asiakasnumero from varastoliikenne where asiakasnumero = ?;");

      // Asetetaan argumentit sql-kyselyyn
      ps.setInt(1, customerID);
      rs = ps.executeQuery();// Hae annetulla käyttäjänimellä
      // tietokanta rivi

      while (rs.next()) {
        int id = rs.getInt("varastoliikenneID");
        int tyyppi = rs.getInt("tyyppi");
        Date pvm = rs.getDate("pvm");
        String osoite = rs.getString("toimitusosoite");
        int userid = rs.getInt("tyontekijaID");
        int asiakasid = rs.getInt("asiakasnumero");
        Varastoliikenne vl = new Varastoliikenne(tyyppi, pvm, osoite, userid, asiakasid, id);
        vl.setAsiakas(asiakas);
        res.add(vl);
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
    return res;
  }

  public int autoincrement() {
    int id = 0;
    try {
      ps = conn.prepareStatement(
          "SELECT AUTO_INCREMENT FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'varasto' AND TABLE_NAME = 'varastoliikenne';");

      rs = ps.executeQuery();// Hae annetulla käyttäjänimellä

      while (rs.next()) {
        id = rs.getInt("AUTO_INCREMENT");
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
    return id;
  }

}