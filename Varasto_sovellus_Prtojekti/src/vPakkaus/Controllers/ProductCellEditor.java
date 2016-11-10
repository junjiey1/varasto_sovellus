package vPakkaus.Controllers;

import vPakkaus.Product;

public class ProductCellEditor extends EditingCell{

	private Product[] PaivitettavatTuotteet;
	private Product muokattava;

	public ProductCellEditor(int n, Product[] PaivitettavatTuotteet) {
		super(n);
		this.PaivitettavatTuotteet = PaivitettavatTuotteet;
		setEditoitavaInstance();
	}
	@Override
	public void textFieldHandelerMethod() {
		String s = getTextField().getText();
		System.out.println(s + " " + getDatatyyppi() + " indeksi " + getIndex());
		try {
			switch (getDatatyyppi()) { // säilötyn luvun avulla voidaan
									// päätellä mitä datatyyppiä
									// pitää tallentaa
			case (1): // Int
				Integer i = this.isInt(s);
				if (i != null && i.intValue() != muokattava.getMax_temperature()) {
					System.out.println("yritän");
					setValChanged(true);
					paivitaSolu(i, getIndex());
				}
				break;
			case (2): // String
				if (getColumnName().equals("Name") && !s.equals(muokattava.getProduct_name())) {
					setValChanged(true);
					paivitaSolu(s, getIndex());
				}
				break;
			case (3):// Double
				Double d = isNumeric(s) ? Double.parseDouble(s) : null;
				if (d != null) {
					// Meillä atm. kaksi riviä, jotka käyttää Double
					// arvoa. Paino ja tilavuus
					if (getColumnName().equals("Weight") && d.doubleValue() != muokattava.getProduct_weight()) { // Muokataan
																											// weight
																											// solua
						setValChanged(true);
						paivitaSolu(d, getIndex());
					} else if (getColumnName().equals("Volume")
							&& d.doubleValue() != muokattava.getProduct_volume()) { // Muokataan
																					// volume
																					// solua
						setValChanged(true);
						paivitaSolu(d, getIndex());
					}
				}
				break;
			case (4):// Float
				Float f = isNumeric(s) ? Float.parseFloat(s) : null;
				if (f != null && getColumnName().equals("Price")
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
		Product p = super.getTableView().getItems().get(i);
		switch (getDatatyyppi()) {
		case (1):
			p.setMax_temperature(((Integer) newValue).intValue());
			break;
		case (2):
			p.setProduct_name(newValue.toString());
			break;
		case (3):
			if (getTextField().equals("Weight")) {
				p.setProduct_weight(((Double) newValue).doubleValue());
			} else {
				p.setProduct_volume(((Double) newValue).doubleValue());
			}
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
