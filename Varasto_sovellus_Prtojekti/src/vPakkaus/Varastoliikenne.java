package vPakkaus;

import java.sql.Date;

public class Varastoliikenne {

  private String osoite;
  private int userID, asiaksID, tyyppi, varastoliikenneID;
  private Date pvm;

  public Varastoliikenne(int tyyppi, Date pvm, String osoite, int userID, int asiaksID) {
    this.osoite = osoite;
    this.userID = userID;
    this.asiaksID = asiaksID;
    this.tyyppi = tyyppi;
    this.pvm = pvm;
  }

  public String getOsoite() {
    return osoite;
  }

  public void setOsoite(String osoite) {
    this.osoite = osoite;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public int getAsiaksID() {
    return asiaksID;
  }

  public void setAsiaksID(int asiaksID) {
    this.asiaksID = asiaksID;
  }

  public int getTyyppi() {
    return tyyppi;
  }

  public void setTyyppi(int tyyppi) {
    this.tyyppi = tyyppi;
  }

  public int getVarastoliikenneID() {
    return varastoliikenneID;
  }

  public void setVarastoliikenneID(int varastoliikenneID) {
    this.varastoliikenneID = varastoliikenneID;
  }

  public Date getPvm() {
    return pvm;
  }

  public void setPvm(Date pvm) {
    this.pvm = pvm;
  }

}
