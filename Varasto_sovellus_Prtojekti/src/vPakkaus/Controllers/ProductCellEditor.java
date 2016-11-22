package vPakkaus.Controllers;

import vPakkaus.Product;

public class ProductCellEditor extends EditingCell{

	private Product[] paivitettavatTuotteet;
	private Product muokattava;

	public ProductCellEditor(int n, Product[] PaivitettavatTuotteet) {
		super(n);
		this.paivitettavatTuotteet = PaivitettavatTuotteet;
	}
	@Override
	public void textFieldHandelerMethod() {
		String s = getTextField().getText();
		//System.out.println(s + " " + getDatatyyppi() + " indeksi " + getIndex() + " NIMI " + getColumnName());
		try {
			switch (getDatatyyppi()) { // säilötyn luvun avulla voidaan
									// päätellä mitä datatyyppiä
									// pitää tallentaa
			case (1): // Int
				Integer i = this.isInt(s);
				if (i != null) {
					setValChanged(true);
					paivitaTuotteenLampotila(i, getIndex());
					paivitaSolu(i, getIndex());
				}
				break;
			case (2): // String
				if (getColumnName().equals("Nimi") && !s.equals(muokattava.getProduct_name())) {
					muokattava.setProduct_name(s);
				  setValChanged(true);
					paivitaSolu(s, getIndex());
				}
				break;
			case (3):// Double
				Double d = isNumeric(s) ? Double.parseDouble(s) : null;
				if (d != null) {
					// Meillä atm. neljä riviä, jotka käyttää Double
					// arvoa. Paino, leveys, pituus ja korkeus
					if (getColumnName().equals("Paino") && d.doubleValue() != muokattava.getProduct_weight()) {
						muokattava.setProduct_weight(d);
					  setValChanged(true);
						paivitaSolu(d, getIndex());
					} else if (getColumnName().equals("Leveys")
							&& d.doubleValue() != muokattava.getProduct_width()) {
						muokattava.setProduct_width(d);
					  setValChanged(true);
						paivitaSolu(d, getIndex());
					} else if(getColumnName().equals("Pituus")
							&& d.doubleValue() != muokattava.getProduct_length()){
					  muokattava.setProduct_length(d);
						setValChanged(true);
						paivitaSolu(d, getIndex());
					} else if(getColumnName().equals("Korkeus")
							&& d.doubleValue() != muokattava.getProduct_height()){
					  muokattava.setProduct_height(d);
						setValChanged(true);
						paivitaSolu(d, getIndex());
					}
				}
				break;
			case (4):// Float
				Float f = isNumeric(s) ? Float.parseFloat(s) : null;
				if (f != null && getColumnName().equals("Hinta")
						&& Float.compare(f, muokattava.getProduct_price()) != 0) {
					setValChanged(true);
					muokattava.setProduct_price(f);
					paivitaSolu(Float.parseFloat(s), getIndex());
				}
				break;

				default:
				  System.out.println("DEFAULT HAARASSA");
			}
			if (isValChangedByUser()) {
				merkitseRivi();
				setValChangedByUser(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("VIRHE HAVAITTU!!!");
		}
	}

	private void paivitaTuotteenLampotila(Integer uusiArvo, int index){
	  if (getColumnName().equals("MaxLampo"))
      muokattava.setMax_temperature(uusiArvo.intValue());
    else
      muokattava.setMin_temperature(uusiArvo.intValue());
    if(muokattava.getMax_temperature()!=null && muokattava.getMin_temperature()!=null)
      if(!muokattava.getTemp()) //productille ei olla säädetty vielä lämpötila booleaniksi true
        muokattava.setTemp(true);
	}

	@Override
	protected void paivitaSolu(Object newValue, int i) {
		setText(newValue.toString());
		paivitettavatTuotteet[i] = muokattava;
		this.cancelEdit();
	}

	@Override
	public void setEditoitavaInstance() {
		muokattava = (Product) getTableRow().getItem();
	}

}
