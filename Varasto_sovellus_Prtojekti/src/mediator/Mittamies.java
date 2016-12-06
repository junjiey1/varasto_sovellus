package mediator;

import java.util.Random;

public class Mittamies {

  public double mittaaHyppy() {
    Random r = new Random();
    double randomValue = 90 + (120 - 90) * r.nextDouble();
    return randomValue;
  }

}
