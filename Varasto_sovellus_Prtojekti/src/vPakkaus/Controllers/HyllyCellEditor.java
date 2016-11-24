package vPakkaus.Controllers;

import vPakkaus.Hyllypaikka;
import vPakkaus.Product;

public class HyllyCellEditor extends EditingCell{

  private Hyllypaikka[] paivitettavatHyllyt;
  private Hyllypaikka muokattava;

  public HyllyCellEditor(int n, Hyllypaikka[] paivitettavat) {
    super(n);
    paivitettavatHyllyt = paivitettavat;
  }

  @Override
  public void setEditoitavaInstance() {
    muokattava = (Hyllypaikka) getTableRow().getItem();
  }

  @Override
  public void textFieldHandelerMethod() {
    String s = getTextField().getText();
    //System.out.println(s + " " + getDatatyyppi() + " indeksi " + getIndex() + " NIMI " + getColumnName());
    try {
      switch (getDatatyyppi()) { // säilötyn luvun avulla voidaan
          case 1:
            System.out.println("yrittää");
            int newLampotila = Integer.parseInt(s);
            muokattava.setLämpötila(newLampotila);
            setValChanged(true);
            break;

          case 3:
            Double d = Double.parseDouble(s);
            if (d != null) {
              // Meillä atm. neljä riviä HYLLYLLE, jotka käyttää Double
              // arvoa. Max_Paino, leveys, pituus ja korkeus
              if (getColumnName().equals("MaxPaino") && d.doubleValue() != muokattava.getMax_paino()) {
                muokattava.setMax_paino(d);
                setValChanged(true);
                paivitaSolu(d, getIndex());
              } else if (getColumnName().equals("Leveys")
                  && d.doubleValue() != muokattava.getLeveys()) {
                System.out.println("saatu leveys : " + muokattava.getLeveys() + " " + d);
                muokattava.setLeveys(d);
                setValChanged(true);
                paivitaSolu(d, getIndex());
              } else if(getColumnName().equals("Pituus")
                  && d.doubleValue() != muokattava.getPituus()){
                System.out.println("saatu pituus : " + muokattava.getPituus() + " " + d);
                muokattava.setPituus(d);
                setValChanged(true);
                paivitaSolu(d, getIndex());
              } else if(getColumnName().equals("Korkeus")
                  && d.doubleValue() != muokattava.getKorkeus()){
                muokattava.setKorkeus(d);;
                setValChanged(true);
                paivitaSolu(d, getIndex());
              }
            }
            break;

          default:
            System.out.println("Default haarassa HyllyCellEditorissa");
      }
      if (isValChangedByUser()) {
        merkitseRivi();
        setValChangedByUser(false);
      }
    }catch(Exception e){
      System.out.println("Virhe havaittu hyllyä muokattaessa");
    }
  }

  @Override
  protected void paivitaSolu(Object newValue, int i) {
    setText(newValue.toString());
    paivitettavatHyllyt[i] = muokattava;
    this.cancelEdit();
  }

}
