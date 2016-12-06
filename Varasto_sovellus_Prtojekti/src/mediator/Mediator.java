package mediator;

import java.util.ArrayList;

public class Mediator {

  private ArrayList<Hyppääjä> hyppääjät = new ArrayList();
  private ArrayList<Tuomari> tuomarit = new ArrayList();
  private Tulostaulu tulostaulu;
  private Mittamies mittamies;
  private Kisasihteeri sih;

  public Mediator() {

  }

  public void addTulostaulu(Tulostaulu tulostaulu) {
    this.tulostaulu = tulostaulu;
  }

  public void addHyppääjä(Hyppääjä hyppääjä) {
    hyppääjät.add(hyppääjä);
  }

  public void addTuomari(Tuomari tuomari) {
    this.tuomarit.add(tuomari);
  }

  public void addMittamies(Mittamies mittamies) {
    this.mittamies = mittamies;
  }

  public void addSih(Kisasihteeri sih) {
    this.sih = sih;
  }

  public void Hyppää(Hyppääjä hyppääjä) {
    ArrayList<Double> tpisteet = new ArrayList();

    for (Tuomari t : this.tuomarit) {
      tpisteet.add(t.arvosteleHyppy());
    }

    double pituus = mittamies.mittaaHyppy();
    Hyppy hyppy = new Hyppy(pituus, tpisteet);
    hyppääjä.setHyppy(hyppy);
  }

  public void näytäTulostaulu() {

    for (Hyppääjä h : hyppääjät) {
      for (Hyppy hy : h.getHypyt()) {
        double yhtpisteet = sih.laskeHypynPisteet(hy.getPisteet(), hy.getLength(), tuomarit.size());
        Tulosrivi tr = new Tulosrivi(h, yhtpisteet);
        tulostaulu.setTulos(tr);
      }
    }
    tulostaulu.haeTulokset();
  }

}
