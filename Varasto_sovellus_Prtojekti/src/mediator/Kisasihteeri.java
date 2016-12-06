package mediator;

import java.util.ArrayList;

public class Kisasihteeri {

  public double laskeHypynPisteet(ArrayList<Double> tp, double pituus, int tuomarit) {
    double pisteet = 0;
    for (Double d : tp) {
      pisteet = pisteet + d;
    }
    pisteet = (pisteet / tuomarit) + pituus;
    return pisteet;
  }

}
