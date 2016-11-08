package vPakkaus.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import vPakkaus.DAO_Objekti;
import vPakkaus.Product;


/**
 * Tämä luokka vastaa tuotteiden taulukon solujen muokkauksesta
 *
 */
abstract class EditingCell extends TableCell<Product, Object> {

	private TextField textField;
	//private Product[] PaivitettavatTuotteet;
	private int datatyyppi;// 1 Int,2 String,3 double,5 float

	//private Product muokattava;// Olio jota muokataan
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
	public EditingCell(int n) {
		//Product[] PaivitettavatTuotteet
//		this.PaivitettavatTuotteet = PaivitettavatTuotteet;
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
			setEditoitavaInstance();
			columnName = this.getTableColumn().getText();
			createTextField();
			setGraphic(textField);
			textField.selectAll();
		}
	}

	public abstract void setEditoitavaInstance();
	//muokattava = (Product) getTableRow().getItem();
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

	protected void setValChanged(boolean b) {
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
					textFieldHandelerMethod();
				}
			}
		});}

	public abstract void textFieldHandelerMethod();

	/**
	*Tätä metodia kutsutaan kun käyttäjä on lopettanut editoinnin ja tekstiarvon arvo on eri kuin alkuperäinen arvo.
	* Solun tekstiarvo päivitetään jos ja vain jos alkuperäinen arvo!=solunarvo.
	*/
	protected abstract void paivitaSolu(Object newValue, int i);

	/**
	*Merkitsee muokatun solun rivin punaiseksi. Kutsutaan vain jos käyttäjä on muokannut solun arvoa
	*/
	protected void merkitseRivi() {
		this.getTableRow().setStyle("-fx-background-color:lightcoral");
	}

	/**
	*Onko String numeerinen
	*@param String s
	*@return true = on numeerinen, false = ei ole numeerinen
	*/
	protected boolean isNumeric(String s) {
		return s.matches("[-+]?\\d*\\.?\\d+");
	}

	/**
	*Onko String int
	*@param String s
	*@return Integer
	*/
	protected Integer isInt(String s) {
		try {
			Integer i = Integer.parseInt(s);
			return i;
		} catch (Exception e) {
			return null;
		}
	}

	protected String getString() {
		return getItem() == null ? "" : getItem().toString();
	}

	public TextField getTextField() {
		return textField;
	}

	public void setTextField(TextField textField) {
		this.textField = textField;
	}

	public int getDatatyyppi() {
		return datatyyppi;
	}

	public void setDatatyyppi(int datatyyppi) {
		this.datatyyppi = datatyyppi;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public boolean isValChangedByUser() {
		return valChangedByUser;
	}

	public void setValChangedByUser(boolean valChangedByUser) {
		this.valChangedByUser = valChangedByUser;
	}

}
