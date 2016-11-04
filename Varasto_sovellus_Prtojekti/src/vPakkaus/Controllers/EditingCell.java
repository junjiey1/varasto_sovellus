package vPakkaus.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import vPakkaus.Product;


/**
 * Tämä luokka vastaa tuotteiden taulukon solujen muokkauksesta
 *
 */
class EditingCell extends TableCell<Product, Object> {

	private TextField textField;
	private Product[] PaivitettavatTuotteet;
	private int datatyyppi;// 1 Int,2 String,3 double,5 float
	private Product muokattava;// Olio jota muokataan
	private String columnName; // Product olion atribuutin nimi eli taulukon
								// kolumnin nimi
	private boolean valChangedByUser;// Boolean, joka asetetaan aina true kun
										// käyttäjä tekee muutoksia taulukkoon
	private boolean escPressed = false;// Boolean, joka muuttu true vain jos
										// käyttäjä painaa esc kun on
										// editoimassa solua

	/**
	 * Tämän luokan konstruktori. Int arvo viittaa solun kapseloivaan tietotyyppiin eli 1.int, 2.string
	 *, 3.double, 4.float. Lista parametri viittaa listaan, joka säilyttää viittaukset muokattuihin tuotteisiin
	 * @param int tietotyyppiNumero, Product[] PaivitettavatTuotteet
	 */
	public EditingCell(int n, Product[] PaivitettavatTuotteet) {
		this.PaivitettavatTuotteet = PaivitettavatTuotteet;
		datatyyppi = n;
		valChangedByUser = false;
		escPressed = false;
	}

	/**
	*Tämä metodi käynnistyy kun käyttäjä tuplaklikkaa taulukossa olevaa solua.
	* Luo tekstikentän josta voidaan muokata tuotteen atribuuttia.
	*/
	@Override
	public void startEdit() {
		if (!isEmpty()) {
			valChangedByUser = false;
			super.startEdit();
			muokattava = (Product) getTableRow().getItem();
			columnName = this.getTableColumn().getText();
			createTextField();
			setGraphic(textField);
			textField.selectAll();
		}
	}

	/**
	*Tämä metodi käynnistyy kun käyttäjä peruuttaa solun editoinnin.
	*/
	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setGraphic(null);
	}

	/**
	*TableCellin oma metodi.
	*/
	@Override
	public void updateItem(Object item, boolean empty) {
		if (valChangedByUser) { // Jos käyttäjä tehnyt muutoksen merkitään se
								// rivi näkymässä
			this.getTableRow().setStyle("-fx-background-color:lightcoral");// Muutetaan
																			// rivin
																			// väriä
			valChangedByUser = false;
		}
		super.updateItem(item, empty);
		if (empty) {
			// setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				if (textField != null) {
					textField.setText(getString());
				}
				// setText(null);
				setGraphic(textField);
			} else {
				setText(getString());
				setGraphic(null);
			}
		}
	}

	private void setValChanged(boolean b) {
		valChangedByUser = b;
	}

	/**
	*Kun editointi käynnistyy tämä metodi luo tekstikentän johon käyttäjän on tarkoitus kirjoittaa solulle uusiarvo.
	*
	*/
	private void createTextField() {
		textField = new TextField(getString());
		EventHandler<KeyEvent> Keylistener = new EventHandler<KeyEvent>() {
			// Luodaan nappi kuuntelija aina kun solun muokkaus alkaa.
			// Kuuntelija asettaa escPressed-boolean arvon=>true jos käyttäjä
			// painaa esc
			// jos ko. boolean arvo on true solun editointi lopetetaan.
			@Override
			public void handle(KeyEvent event) {
				System.out.println(event.getCode().getName());
				if (event.getCode().getName().equals("Esc"))
					escPressed = true;
			}
		};
		textField.setOnKeyPressed(Keylistener);
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() { // Tässä
																				// määritellään
																				// uusi
																				// sisäluokka
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				System.out.println(arg1 + " " + arg2);
				if (escPressed) {
					escPressed = false;
					cancelEdit();
					return;
				}
				if (!arg2) {
					String s = textField.getText();
					System.out.println(s + " " + datatyyppi + " indeksi " + getIndex());
					try {
						switch (datatyyppi) { // säilötyn luvun avulla voidaan
												// päätellä mitä datatyyppiä
												// pitää tallentaa
						case (1): // Int
							Integer i = isInt(s);
							if (i != null && i.intValue() != muokattava.getMaara()) {
								System.out.println("yritän");
								setValChanged(true);
								paivitaSoluJaTuote(i, getIndex());
							}
							break;
						case (2): // String
							if (columnName.equals("Name") && !s.equals(muokattava.getProduct_name())) {
								setValChanged(true);
								paivitaSoluJaTuote(s, getIndex());
							}
							break;
						case (3):// Double
							Double d = isNumeric(s) ? Double.parseDouble(s) : null;
							if (d != null) {
								// Meillä atm. kaksi riviä, jotka käyttää Double
								// arvoa. Paino ja tilavuus
								if (columnName.equals("Weight") && d.doubleValue() != muokattava.getProduct_weight()) { // Muokataan
																														// weight
																														// solua
									setValChanged(true);
									paivitaSoluJaTuote(d, getIndex());
								} else if (columnName.equals("Volume")
										&& d.doubleValue() != muokattava.getProduct_volume()) { // Muokataan
																								// volume
																								// solua
									setValChanged(true);
									paivitaSoluJaTuote(d, getIndex());
								}
							}
							break;
						case (4):// Float
							Float f = isNumeric(s) ? Float.parseFloat(s) : null;
							if (f != null && columnName.equals("Price")
									&& Float.compare(f, muokattava.getProduct_price()) != 0) {
								setValChanged(true);
								paivitaSoluJaTuote(Float.parseFloat(s), getIndex());
							}
							break;
						}
						if (valChangedByUser) {
							merkitseRivi();
							valChangedByUser = false;
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("VIRHE HAVAITTU!!!");
					}
				}
			}
		});
	}

	/**
	*Tätä metodia kutsutaan kun käyttäjä on lopettanut editoinnin ja tekstiarvon arvo on eri kuin alkuperäinen arvo.
	* Solun tekstiarvo päivitetään jos ja vain jos alkuperäinen arvo!=solunarvo.
	*/
	private void paivitaSoluJaTuote(Object newValue, int i) {
		setText(newValue.toString());
		Product p = super.getTableView().getItems().get(i);
		switch (datatyyppi) {
		case (1):
			p.setMaara(((Integer) newValue).intValue());
			break;
		case (2):
			p.setProduct_name(newValue.toString());
			break;
		case (3):
			if (columnName.equals("Weight")) {
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

	/**
	*Merkitsee muokatun solun rivin punaiseksi. Kutsutaan vain jos käyttäjä on muokannut solun arvoa
	*/
	private void merkitseRivi() {
		this.getTableRow().setStyle("-fx-background-color:lightcoral");
	}

	/**
	*Onko String numeerinen
	*@param String s
	*@return true = on numeerinen, false = ei ole numeerinen
	*/
	private boolean isNumeric(String s) {
		return s.matches("[-+]?\\d*\\.?\\d+");
	}

	/**
	*Onko String int
	*@param String s
	*@return Integer
	*/
	private Integer isInt(String s) {
		try {
			Integer i = Integer.parseInt(s);
			return i;
		} catch (Exception e) {
			return null;
		}
	}

	private String getString() {
		return getItem() == null ? "" : getItem().toString();
	}
}
