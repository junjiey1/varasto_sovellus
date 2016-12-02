package vPakkaus.Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.Region;
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
	boolean allGood, noErrorsEncountered;

	private ObservableList<String> productTextFiles = FXCollections.observableArrayList();

	private File file;
	private Scanner input;
	private String[] oneRowOfData;
	private String path, pName, pShelf, clientName, clientAddress, fileName, errorLog;
	private double pWeight, pLength, pWidth, pHeight;
	private int pQuantity, fileRow;
	private Integer pMinTemp, pMaxTemp;
	private float pPrice;
	private boolean lisataanManuaalisesti;
	
	private TreeMap<String, String> tmap;

	public AddProductController(){
	  tmap = new TreeMap<String, String>();
		pMinTemp=pMaxTemp=null;
		lisataanManuaalisesti = false;
		errorLog="";
		fileRow=1;
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
		noErrorsEncountered = true;
		allGood = true;

		if (productName.getText().isEmpty() || quantity.getText().isEmpty() || price.getText().isEmpty()
				|| weight.getText().isEmpty() || volume.getText().isEmpty() || whLocation.getText().isEmpty()) {
			allGood = false;
		}

		allGood = validoiTekstikentanMuuttujat();

		if (allGood) {

			//int lisaajan_id = mc.getID();
			Tuotejoukko joukko = rakennaTuotejoukko();
			noErrorsEncountered = mc.addProduct(joukko);
			if (!noErrorsEncountered) {
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
	  String Fname = null;
	  Fname = productTextFiles.get(productTextFiles.indexOf(productList.getSelectionModel().getSelectedItem()));
	  productTextFiles.remove(productTextFiles.indexOf(productList.getSelectionModel().getSelectedItem()));
//	  for(Map.Entry<String, String> entry : tmap.entrySet()){
//	    System.out.println(entry.getKey()+"    "+ entry.getValue());
//	  }
	  tmap.remove(Fname);
	}

	/**ww
	 * Poista kaikki teksti tiedostot
	 */
	public void removeAllProducts() {
		productTextFiles.clear();
		tmap.clear();
	}

	/**
	 * Lisaa tiedostoissa olevat tavarat tietokantaan.
	 *
	 * @throws FileNotFoundException
	 *             Jos tiedosto ei löydy.
	 */
	public void addAllFromFile() throws FileNotFoundException {
		for (String s : productTextFiles) {
			readFromFile(tmap.get(s));
		}
		tmap.clear();
	}
	
	private boolean validoiTekstikentanMuuttujat(){
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
	      System.out.println("true");
	      return true;
	    } catch (NumberFormatException ex) {
	      allGood = false;
	      //virheIlmoitus("Tuotteen " + pName + " muuttujissa havaittiin virhe!");
	      return false;
	    }
	}

	private boolean validoiListanMuuttujat(String[] rowOfData){
	  try{
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
	    return true;
	  }catch (NumberFormatException ex) {
      return false;
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
					tmap.put(new String(fileName), new String(path));
					productTextFiles.add(fileName);
					productList.setItems(productTextFiles);
				}

				// ONLY 1 FILE DRAGGED
			} else {
				path = path.substring(1, path.length() - 1);
				index = path.lastIndexOf("\\");
				fileName = path.substring(index + 1, path.length());
				productTextFiles.add(fileName);
				tmap.put(new String(fileName), new String(path));
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
		noErrorsEncountered = true;
		lisataanManuaalisesti = true;
		file = new File(name); //Ei pysty lukemaan kahta eri tiedostoa joiden nimet eroavat. Antaa null pointer exception
		input = new Scanner(file);
		fileRow=1;
		while (input.hasNext()) {
			oneRowOfData = input.nextLine().split(",");
			if (oneRowOfData.length == 2) {
				clientName = oneRowOfData[0];
				clientAddress = oneRowOfData[1];
			} else {
			  if(!validoiListanMuuttujat(oneRowOfData))
			    virheIlmoitus("Rivillä : " + fileRow + " Tuotteen " + pName + " muuttujissa havaittiin virhe!");
			  else
			    noErrorsEncountered = mc.addProduct(rakennaTuotejoukko());   //Jos mc.addProduct antaa errorin niin pääkontrolleri lähettää tälle luokalle errorin mikä lisätään errorLogiin
			}
			fileRow++;
		}
		lisataanManuaalisesti = false;
		input.close();
		boolean err;
		String text;
		if(!errorLog.equals("")){
		  text = "Joitakin tuotteita ei voitu lisätä!";
		  err = true;
		}else{
		  text = "Tuotteet lisättiin onnistuneesti!";
      err = false;
		}

		if(showInfoToUser(text, err)){ //jos errorLogi ei ole tyhjä niin lisäys operaation yhteydessä tapahtui virhe
		  errorLog = "Seuraavia tuotteita ei voitu lisätä tiedostosta" + fileName + ":\n".concat(errorLog);
		  virheIlmoitus(errorLog);
		}
		errorLog="";
	}

	private boolean showInfoToUser(String text, boolean errorOccured){
	  Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("TIEDOTE");
    alert.setContentText(text);
    ButtonType buttonTypeOne = ButtonType.OK;
    alert.getButtonTypes().setAll(buttonTypeOne);
    if(errorOccured){
      ButtonType buttonTypeTwo = new ButtonType("Virheiden lisätiedot");
      alert.getButtonTypes().add(buttonTypeTwo);
      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == buttonTypeTwo){
        return true;
      }
    }
    else
      alert.showAndWait();
    return false;
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
	  if(lisataanManuaalisesti){
	      errorLog = errorLog.concat("Virhe rivillä " + fileRow + " : " + viesti.toString() + "\n");
	  }else{
  	  Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error");
      String version = System.getProperty("java.version");
      String content = String.format(viesti.toString(), version);
      alert.setContentText(content);
      alert.showAndWait();
    }
	}

	@Override
	public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
		this.vaihtaja = vaihtaja;
		vaihtaja.rekisteröiNakymaKontrolleri(this, "addpage");
	}

	@Override
	public void esiValmistelut() {
	  productName.setPromptText("Tuotteen nimi");
	}

}
