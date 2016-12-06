package mediator;

import java.util.Random;

public class Tuomari {

  public double arvosteleHyppy() {
    Random r = new Random();
    double randomValue = 16 + (20 - 16) * r.nextDouble();
    return randomValue;
  }
}
