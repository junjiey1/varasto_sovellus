package mediator;

import java.util.ArrayList;

public class Hyppy {

  private double length;
  private ArrayList<Double> pisteet;

  public Hyppy(double length, ArrayList<Double> pisteet) {
    this.length = length;
    this.pisteet = pisteet;
  }

  public double getLength() {
    return length;
  }

  public void setLength(double length) {
    this.length = length;
  }

  public ArrayList<Double> getPisteet() {
    return pisteet;
  }

  public void setPisteet(ArrayList<Double> pisteet) {
    this.pisteet = pisteet;
  }
}
