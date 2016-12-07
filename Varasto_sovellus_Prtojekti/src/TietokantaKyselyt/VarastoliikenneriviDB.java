package TietokantaKyselyt;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vPakkaus.DB_AccessObject;
import vPakkaus.Product;
import vPakkaus.Tuotejoukko;
import vPakkaus.Varastoliikenne;
import vPakkaus.Varastoliikennerivi;

public class VarastoliikenneriviDB {

  private Connection conn = null;
  private PreparedStatement ps = null;
  private ResultSet rs = null;
  private ProductDB productdb = null;
  private VarastoliikenneDB varastoliikennedb = null;
  private DB_AccessObject db = null;

  public VarastoliikenneriviDB(Connection conn, ProductDB productdb,
      VarastoliikenneDB varastoliikennedb, DB_AccessObject db) {
    this.conn = conn;
    this.productdb = productdb;
    this.varastoliikennedb = varastoliikennedb;
    this.db = db;
    // TODO Auto-generated constructor stub
  }

  public boolean CreateVarastoliikennerivi(Varastoliikennerivi vr) {
    System.out.println(vr.getTuoteID() + " " + vr.getVarastoliikenneID() + " " + vr.getMaara());
    try {
      ps = conn.prepareStatement(
          "INSERT INTO varastoliikennerivi(tuoteID, varastoliikenneID, maara)" + "VALUES (?,?,?);");
      // haetaan tuotteen id tietokannasta

      ps.setInt(1, vr.getTuoteID());
      ps.setInt(2, vr.getVarastoliikenneID());
      ps.setInt(3, vr.getMaara());

      ps.executeUpdate();
      ps.close();

    } catch (SQLException e) {
      System.out.println("Lisäys epäonnistui varastoliikennerivitaulukkoon!");
      db.setErrorMsg(e.getMessage());
      System.out.println(e.getMessage());
      // e.printStackTrace();
      return false;
    }
    return true;
  }

  public Varastoliikennerivi findVarastoliikennerivi(int riviid) {
    Varastoliikennerivi vlr = null;
    try {
      ps = conn.prepareStatement(
          "select rivi_ID, tuoteID, varastoliikenneID, maara from varastoliikennerivi where rivi_ID = ?;");

      // Asetetaan argumentit sql-kyselyyn
      ps.setInt(1, riviid);
      rs = ps.executeQuery();// Hae annetulla käyttäjänimellä
      // tietokanta rivi

      while (rs.next()) {
        int rivi_id = rs.getInt("rivi_ID");
        int tuoteID = rs.getInt("tuoteID");
        int varastoliikenneID = rs.getInt("varastoliikenneID");
        int maara = rs.getInt("maara");

        vlr = new Varastoliikennerivi(tuoteID, varastoliikenneID, maara);
        vlr.setRiviID(rivi_id);
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
    return vlr;
  }

  public Varastoliikenne findVarastoliikennerivit(Varastoliikenne vl){
    try {
      ps = conn.prepareStatement(
          "select rivi_ID, tuoteID, maara from varastoliikennerivi where varastoliikenneID = ?;");

      ps.setInt(1, vl.getVarastoliikenneID());
      rs = ps.executeQuery();

      while (rs.next()) {
        int tuoteID = rs.getInt("tuoteID");
        int maara = rs.getInt("maara");
        Varastoliikennerivi vlr = new Varastoliikennerivi(tuoteID, vl.getVarastoliikenneID(), maara);
        vlr.setTuotejoukko(new Tuotejoukko(productdb.findProductWithID(tuoteID), null, maara));
        vl.addTuotejoukko(vlr);
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

  public boolean deleteVarastoliikennerivit(Product p) {
    try {
      ps = conn.prepareStatement("DELETE FROM varastoliikennerivi WHERE tuoteID = ?");
      ps.setInt(1, p.getID());
      ps.executeUpdate();
      ps.close();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean deleteRivitByID(int id){
    try {
      ps = conn.prepareStatement("DELETE FROM varastoliikennerivi WHERE varastoliikenneID = ?");
      ps.setInt(1, id);
      ps.executeUpdate();
      ps.close();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

}
