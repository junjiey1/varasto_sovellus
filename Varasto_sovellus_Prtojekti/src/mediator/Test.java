package mediator;

public class Test {
  public static void main(String[] args) {
    Mediator mediator = new Mediator();
    Hyppääjä h1 = new Hyppääjä(mediator, "Seppo");
    Hyppääjä h2 = new Hyppääjä(mediator, "Jari");
    Hyppääjä h3 = new Hyppääjä(mediator, "Lari");
    Hyppääjä h4 = new Hyppääjä(mediator, "Kake");
    Hyppääjä h5 = new Hyppääjä(mediator, "Jeppe");
    Tulostaulu t = new Tulostaulu(mediator);
    Kisasihteeri sih = new Kisasihteeri();
    Mittamies m = new Mittamies();
    Tuomari t1 = new Tuomari();
    Tuomari t2 = new Tuomari();
    Tuomari t3 = new Tuomari();
    Tuomari t4 = new Tuomari();
    Tuomari t5 = new Tuomari();
    mediator.addSih(sih);
    mediator.addTulostaulu(t);
    mediator.addMittamies(m);
    mediator.addHyppääjä(h1);
    mediator.addHyppääjä(h2);
    mediator.addHyppääjä(h3);
    mediator.addHyppääjä(h4);
    mediator.addHyppääjä(h5);
    mediator.addTuomari(t1);
    mediator.addTuomari(t2);
    mediator.addTuomari(t3);
    mediator.addTuomari(t4);
    mediator.addTuomari(t5);
    h1.hyppää();
    h1.hyppää();
    h2.hyppää();
    h2.hyppää();
    h3.hyppää();
    h3.hyppää();
    h4.hyppää();
    h4.hyppää();
    h5.hyppää();
    h5.hyppää();
    t.näytäTulokset();
  }
}
