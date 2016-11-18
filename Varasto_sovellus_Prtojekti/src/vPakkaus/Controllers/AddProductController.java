package vPakkaus.Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import vPakkaus.Hyllypaikka;
import vPakkaus.Product;
import vPakkaus.Tuotejoukko;

/**
 *
 * Kontrolleri tavaran lisaamiselle.
 *
 */
public class AddProductController implements Nakyma_IF {

	@FXML
	private TextField productName, quantity, price, weight, volume, whLocation, length, width, height, minTempT, maxTempT;
	@FXML
	private ListView<String> productList;
    @FXML
    private Label minTempL, maxTempL;

    private NayttojenVaihtaja_IF vaihtaja;
	private MainController_IF mc;
	boolean allGood, product_error;

	ObservableList<String> productTextFiles = FXCollections.observableArrayList();

	File file;
	Scanner input;
	String[] oneRowOfData;
	String path, pName, pShelf, clientName, clientAddress, fileName;
	double pWeight, pLength, pWidth, pHeight;
	int pQuantity;
	Integer pMinTemp, pMaxTemp;
	float pPrice;
	private HashMap<String, String> hm;

	public AddProductController(){
		pMinTemp=pMaxTemp=null;
	}

	public void setMainController(MainController_IF m) {
		mc = m;
	}

	/**
	 * Tavara lisaaminen manuaalisesti
	 *
	 * @throws IOException
	 *             Jos lisaaminen epaonnistunut
	 */
	public void addProductManually() throws IOException {
		product_error = true;
		allGood = true;

		if (productName.getText().isEmpty() || quantity.getText().isEmpty() || price.getText().isEmpty()
				|| weight.getText().isEmpty() || volume.getText().isEmpty() || whLocation.getText().isEmpty()) {
			allGood = false;
		}

		try {
			pName = productName.getText();
			pWeight = Double.parseDouble(weight.getText());
			pWidth = Double.parseDouble(width.getText());
			pHeight = Double.parseDouble(height.getText());
			pQuantity = Integer.parseInt(quantity.getText());
			pPrice = Float.parseFloat(price.getText());
			pShelf = whLocation.getText();
			pLength = Double.parseDouble(length.getText());
			if(minTempT.getText().equals("") || maxTempT.getText().equals("")){
				//jompikumpi lämpötila tyhjä ei hyväksytä
				pMinTemp=pMaxTemp=null;
			}else{
				pMinTemp = Integer.parseInt(minTempT.getText());
				pMaxTemp = Integer.parseInt(maxTempT.getText());
			}

		} catch (NumberFormatException ex) {
			allGood = false;
		}

		if (allGood) {

			//int lisaajan_id = mc.getID();
			Tuotejoukko joukko = rakennaTuotejoukko();
			product_error = mc.addProduct(joukko);
			if (!product_error) {
				virheIlmoitus("Tuotteiden muuttujissa havaittiin virhe!\nTarkista asettamiesi muuttujien arvot...");
			} else
				JOptionPane.showMessageDialog(null, "uusi tuote lisättiin onnistuneesti", "Lisäys onnistui",
						JOptionPane.INFORMATION_MESSAGE);
		} else {
		  virheIlmoitus("Tuotteiden muuttujissa havaittiin virhe!\nTarkista asettamiesi muuttujien arvot...");
		}
	}

	/**
	 * Poista valittu teksti tiedosto.
	 */
	public void removeProduct() {
		productTextFiles.remove(productTextFiles.indexOf(productList.getSelectionModel().getSelectedItem()));
	}

	/**
	 * Poista kaikki teksti tiedostot
	 */
	public void removeAllProducts() {
		productTextFiles.clear();
	}

	/**
	 * Lisaa tiedostoissa olevat tavarat tietokantaan.
	 *
	 * @throws FileNotFoundException
	 *             Jos tiedosto ei löydy.
	 */
	public void addAllFromFile() throws FileNotFoundException {
		for (String s : productTextFiles) {
			readFromFile(hm.get(s));
		}
	}

	/**
	 *
	 * Kuuntelee, onko tiedostoja siirtamassa automaattinen lisays taulukkoon.
	 *
	 * @param event
	 *            Kuuntelija
	 * @throws FileNotFoundException
	 *             Jos tiedosto ei loydy
	 */
	@FXML
	public void handleFilesDragDropped(DragEvent event) throws FileNotFoundException {
		hm = new HashMap<String, String>();
		Dragboard db = event.getDragboard();

		int index = 0;
		if (db.hasFiles()) {
			path = db.getFiles().toString();

			// MULTIPLE FILES DRAGGED
			if (db.getFiles().size() > 1) {
				oneRowOfData = path.split(",");
				for (int i = 0; i < oneRowOfData.length; i++) {
					if (i == oneRowOfData.length - 1) {
						oneRowOfData[i] = oneRowOfData[i].substring(1, oneRowOfData[i].length() - 1);
					} else {
						oneRowOfData[i] = oneRowOfData[i].substring(1, oneRowOfData[i].length());
					}
					path = oneRowOfData[i];
					index = path.lastIndexOf("\\");
					fileName = path.substring(index + 1, path.length());
					hm.put(new String(fileName), new String(path));
					productTextFiles.add(fileName);
					productList.setItems(productTextFiles);
				}

				// ONLY 1 FILE DRAGGED
			} else {
				path = path.substring(1, path.length() - 1);
				index = path.lastIndexOf("\\");
				fileName = path.substring(index + 1, path.length());
				productTextFiles.add(fileName);
				hm.put(new String(fileName), new String(path));
				productList.setItems(productTextFiles);
			}
			db.clear();
		}
		event.consume();
		db = null;
	}

	/**
	 * Lukee kaikki teksti tiedot tiedostosta.
	 *
	 * @param name
	 *            Tiedosto nimi
	 * @throws FileNotFoundException
	 *             ilmoittaa error, jos on epaonnistunut.
	 */
	public void readFromFile(String name) throws FileNotFoundException {
		product_error = true;
		file = new File(name);
		input = new Scanner(file);

		while (input.hasNext()) {
			oneRowOfData = input.nextLine().split(",");
			if (oneRowOfData.length == 2) {
				clientName = oneRowOfData[0];
				clientAddress = oneRowOfData[1];
			} else {
				pName = oneRowOfData[0];
				pWeight = Double.parseDouble(oneRowOfData[1]);
				pLength = Double.parseDouble(oneRowOfData[2]);
				pWidth = Double.parseDouble(oneRowOfData[3]);
				pHeight = Double.parseDouble(oneRowOfData[4]);
				pShelf = oneRowOfData[5];
				pPrice = Float.parseFloat(oneRowOfData[6]);
				pQuantity = Integer.parseInt(oneRowOfData[7]);
				pMinTemp = Integer.parseInt(oneRowOfData[8]);
				pMaxTemp = Integer.parseInt(oneRowOfData[9]);
				product_error = mc.addProduct(rakennaTuotejoukko());

				if (!product_error) {
				  virheIlmoitus("Tuotteiden muuttujissa havaittiin virhe!");
					break;
				}
				System.out.println(clientName + "  " + clientAddress + " " + pName + " " + pWeight + " " + " "
						+ pShelf + " " + pPrice + " " + pQuantity);
			}
		}
		input.close();
	}

	public void showTemperatures(){
		if (minTempL.isVisible()){
			minTempL.setVisible(false);
			maxTempL.setVisible(false);
			minTempT.setVisible(false);
			maxTempT.setVisible(false);
		}else{
			minTempL.setVisible(true);
			maxTempL.setVisible(true);
			minTempT.setVisible(true);
			maxTempT.setVisible(true);
		}
	}

	public Tuotejoukko rakennaTuotejoukko(){
		Product product = new Product(pName, pWeight,
			pWidth, pHeight,
			pLength, pPrice
		);
		if(pMinTemp!=null && pMaxTemp!=null){
			product.setMin_temperature(pMinTemp);
			product.setMax_temperature(pMaxTemp);
			product.setTemp(true);
			pMinTemp=pMaxTemp=null;
		}
		Hyllypaikka hylly = new Hyllypaikka(pShelf);
		return new Tuotejoukko(product, hylly, pQuantity);
	}

//	public void product_error_handler() {
//		JOptionPane.showMessageDialog(null, "Error occured while adding product, please check product information.");
//	}

	@Override
	public void paivita(Object data) {

	}

	@Override
	public void resetoi() {
	  productName.setText("");
    quantity.setText("");
    price.setText("");
    weight.setText("");
    volume.setText("");
    whLocation.setText("");
    length.setText("");
    width.setText("");
    height.setText("");
    minTempT.setText("");
    maxTempT.setText("");
	}

	@Override
	public void virheIlmoitus(Object viesti) {
	  Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setContentText(viesti.toString());
    alert.showAndWait();
	}

	@Override
	public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
		this.vaihtaja = vaihtaja;
		vaihtaja.rekisteröiNakymaKontrolleri(this, "addpage");
	}

	@Override
	public void esiValmistelut() {

	}

}
