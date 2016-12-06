package mediator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Tulostaulu {
  private Mediator mediator;
  private ArrayList<Tulosrivi> tulokset = new ArrayList();

  public Tulostaulu(Mediator mediator) {
    this.mediator = mediator;
  }

  public ArrayList<Tulosrivi> getTulokset() {
    return tulokset;
  }

  public void addTulos(Tulosrivi tulos) {
    this.tulokset.add(tulos);
  }

  public void näytäTulokset() {
    mediator.näytäTulostaulu();
  }

  public void printTulokset() {
    System.out.println("Tulostaulu:");
    Collections.sort(tulokset, new Comparator<Tulosrivi>() {
      public int compare(Tulosrivi o1, Tulosrivi o2) {
        return new Double(o2.getYhteispisteet()).compareTo(o1.getYhteispisteet());
      }
    });

    for (Tulosrivi t : tulokset) {
      DecimalFormat df = new DecimalFormat("#.0");
      System.out.println(t.getH().getName() + " " + df.format(t.getYhteispisteet()));
    }
  }

}
