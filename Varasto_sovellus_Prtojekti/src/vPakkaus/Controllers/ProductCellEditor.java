package vPakkaus.Controllers;

import vPakkaus.Product;

public class ProductCellEditor extends EditingCell{

	private Product[] PaivitettavatTuotteet;
	private Product muokattava;

	public ProductCellEditor(int n, Product[] PaivitettavatTuotteet) {
		super(n);
		this.PaivitettavatTuotteet = PaivitettavatTuotteet;
	}
	@Override
	public void textFieldHandelerMethod() {
		String s = getTextField().getText();
		System.out.println(s + " " + getDatatyyppi() + " indeksi " + getIndex() + " NIMI " + getColumnName());
		try {
			switch (getDatatyyppi()) { // säilötyn luvun avulla voidaan
									// päätellä mitä datatyyppiä
									// pitää tallentaa
			case (1): // Int
				Integer i = this.isInt(s);
				if (i != null) {
					setValChanged(true);
					paivitaSolu(i, getIndex());
				}
				break;
			case (2): // String
				if (getColumnName().equals("Nimi") && !s.equals(muokattava.getProduct_name())) {
					setValChanged(true);
					paivitaSolu(s, getIndex());
				}
				break;
			case (3):// Double
				Double d = isNumeric(s) ? Double.parseDouble(s) : null;
				if (d != null) {
					// Meillä atm. kaksi riviä, jotka käyttää Double
					// arvoa. Paino ja tilavuus
					if (getColumnName().equals("Paino") && d.doubleValue() != muokattava.getProduct_weight()) { // Muokataan
																											// weight
																											// solua
						setValChanged(true);
						paivitaSolu(d, getIndex());
					} else if (getColumnName().equals("Leveys")
							&& d.doubleValue() != muokattava.getProduct_width()) { // Muokataan
																					// volume
																					// solua
						setValChanged(true);
						paivitaSolu(d, getIndex());
					} else if(getColumnName().equals("Pituus")
							&& d.doubleValue() != muokattava.getProduct_length()){
						setValChanged(true);
						paivitaSolu(d, getIndex());
					} else if(getColumnName().equals("Korkeus")
							&& d.doubleValue() != muokattava.getProduct_height()){
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
					paivitaSolu(Float.parseFloat(s), getIndex());
				}
				break;
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
	@Override
	protected void paivitaSolu(Object newValue, int i) {
		setText(newValue.toString());
		Product p = (Product)super.getTableView().getItems().get(i);
		switch (getDatatyyppi()) {
			case (1):
				if (getColumnName().equals("MaxLampo"))
					p.setMax_temperature(((Integer) newValue).intValue());
				else
					p.setMin_temperature(((Integer) newValue).intValue());
				if(p.getMax_temperature()!=null && p.getMin_temperature()!=null)
					if(!p.getTemp()) //productille ei olla säädetty vielä lämpötila booleaniksi true
						p.setTemp(true);
				break;
			case (2):
				p.setProduct_name(newValue.toString());
				break;
			case (3):
				if (getColumnName().equals("Paino")) {
					p.setProduct_weight(((Double) newValue).doubleValue());
				} else if (getColumnName().equals("Leveys")){
					p.setProduct_width(((Double) newValue).doubleValue());
				} else if(getColumnName().equals("Pituus")){
					p.setProduct_length(((Double) newValue).doubleValue());
				} else if(getColumnName().equals("Korkeus"))
					p.setProduct_height(((Double) newValue).doubleValue());
				break;
			case (4):
				p.setProduct_price(((Float) newValue).floatValue());
		}
		PaivitettavatTuotteet[i] = p;
		this.cancelEdit();
	}
	@Override
	public void setEditoitavaInstance() {
		muokattava = (Product) getTableRow().getItem();
	}

}
