package mediator;

public class Tulosrivi {

  private Hyppääjä h;
  private double yhteispisteet;

  public Tulosrivi(Hyppääjä h, double yhteispisteet) {
    this.h = h;
    this.yhteispisteet = yhteispisteet;
  }

  public Hyppääjä getH() {
    return h;
  }

  public void setH(Hyppääjä h) {
    this.h = h;
  }

  public double getYhteispisteet() {
    return yhteispisteet;
  }

  public void setYhteispisteet(double yhteispisteet) {
    this.yhteispisteet = yhteispisteet;
  }
}
