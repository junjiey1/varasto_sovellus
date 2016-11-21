package vPakkaus.Controllers;

import vPakkaus.Hyllypaikka;

public class HyllyCellEditor extends EditingCell{

  private Hyllypaikka[] paivitettavatHyllyt;

  public HyllyCellEditor(int n, Hyllypaikka[] paivitettavat) {
    super(n);
    paivitettavatHyllyt = paivitettavat;
  }

  @Override
  public void setEditoitavaInstance() {
    // TODO Auto-generated method stub

  }

  @Override
  public void textFieldHandelerMethod() {
    // TODO Auto-generated method stub

  }

  @Override
  protected void paivitaSolu(Object newValue, int i) {
    // TODO Auto-generated method stub

  }

}
