package vPakkaus;

public class Varastoliikennerivi {

  int tuoteID, varastoliikenneID, maara, riviID;

  public Varastoliikennerivi(int tuoteID, int varastoliikenneID, int maara) {
    this.tuoteID = tuoteID;
    this.varastoliikenneID = varastoliikenneID;
    this.maara = maara;
  }

  public Varastoliikennerivi(int tuoteID, int maara) {
    this.tuoteID = tuoteID;
    this.maara = maara;
  }


  public int getTuoteID() {
    return tuoteID;
  }

  public void setTuoteID(int tuoteID) {
    this.tuoteID = tuoteID;
  }

  public int getVarastoliikenneID() {
    return varastoliikenneID;
  }

  public void setVarastoliikenneID(int varastoliikenneID) {
    this.varastoliikenneID = varastoliikenneID;
  }

  public int getMaara() {
    return maara;
  }

  public void setMaara(int maara) {
    this.maara = maara;
  }


  public int getRiviID() {
    return riviID;
  }


  public void setRiviID(int riviID) {
    this.riviID = riviID;
  }

}
