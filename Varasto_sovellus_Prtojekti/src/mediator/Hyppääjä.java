package mediator;

import java.util.ArrayList;

public class Hyppääjä {

  private Mediator mediator;
  private ArrayList<Hyppy> hypyt = new ArrayList();
  private String name;

  public Hyppääjä(Mediator mediator, String name) {
    this.mediator = mediator;
    this.name = name;
  }

  public void hyppää() {
    mediator.Hyppää(this);
  }

  public void setHyppy(Hyppy hyppy) {
    if (hypyt.size() < 2) {
      hypyt.add(hyppy);
    } else {
      System.out.println("Hyppääjä: "+ this.getName() +" on hypännyt jo kaksi hyppyä");
    }
  }

  public ArrayList<Hyppy> getHypyt() {
    return hypyt;
  }

  public void setHypyt(ArrayList<Hyppy> hypyt) {
    this.hypyt = hypyt;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
