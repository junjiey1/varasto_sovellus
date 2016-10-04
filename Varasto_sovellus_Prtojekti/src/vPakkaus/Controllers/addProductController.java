package vPakkaus.Controllers;

import java.awt.datatransfer.Clipboard;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import vPakkaus.CurrentDate;
import vPakkaus.DB_AccessObject;
import vPakkaus.Product;

public class addProductController implements SetMainController{

	@FXML
	private TextField productName;
	@FXML
	private TextField quantity;
	@FXML
	private TextField price;
	@FXML
	private TextField weight;
	@FXML
	private TextField volume;
	@FXML
	private TextField whLocation;

	private MainController mc;
	boolean allGood;

	File file;
	Scanner input;
	String[] oneRowOfData;
	String path, pName, pShelf, clientName, clientAddress;
	double pWeight, pVolume;
	int pQuantity;
	float pPrice;

	public void setMainController(MainController m) {
		mc = m;
	}

	public void AddProductManually() throws IOException {
		allGood = true;

		if (productName.getText().isEmpty() || quantity.getText().isEmpty() || price.getText().isEmpty()
				|| weight.getText().isEmpty() || volume.getText().isEmpty() || whLocation.getText().isEmpty()) {
			allGood = false;
		}

		try {
			Integer.parseInt(quantity.getText());
			Double.parseDouble(volume.getText());
			Double.parseDouble(weight.getText());
			Float.parseFloat(price.getText());
		} catch (NumberFormatException ex) {
			allGood = false;
		}

		if (allGood) {

			int lisaajan_id = mc.getID();

			Product product = new Product(productName.getText(), whLocation.getText(),
					Double.parseDouble(weight.getText()), Double.parseDouble(volume.getText()),
					Float.parseFloat(price.getText()));

			mc.AddProduct(product.getProduct_name(), product.getProduct_weight(), product.getProduct_volume(),
					product.getProduct_location(), product.getProduct_price(), Integer.parseInt(quantity.getText()));


		} else {
			System.out.println("joku kenttä on tyhjä tai väärin täytetty");
		}
	}

	// public boolean addNewProduct(Product product){
	// Lisaa(product.getProduct_name(), product.getProduct_weight(),
	// product.getProduct_volume(), product.getProduct_location(),
	// product.getProduct_price());
	//
	// }

	@FXML
	public void handleFilesDragDropped(DragEvent event) throws FileNotFoundException {
		Dragboard db = event.getDragboard();
		System.out.println(db.getFiles().size());

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
					readFromFile(oneRowOfData[i]);
				}

			// ONLY 1 FILE DRAGGED
			} else {
				path = path.substring(1, path.length()-1);
				readFromFile(path);
			}
			db.clear();
		}
		event.consume();
		db = null;
	}

	public void readFromFile(String name) throws FileNotFoundException {

		file = new File(name);
		//SSKANKDKFAQBFBQAFOB
		input = new Scanner(file);
		boolean product_error = true;

		while (input.hasNext()) {
			oneRowOfData = input.nextLine().split(",");
			if (oneRowOfData.length == 2){
				clientName = oneRowOfData[0];
				clientAddress = oneRowOfData[1];
			}else{
				pName = oneRowOfData[0];
				pWeight = Double.parseDouble(oneRowOfData[1]);
				pVolume = Double.parseDouble(oneRowOfData[2]);
				pShelf = oneRowOfData[3];
				pPrice = Float.parseFloat(oneRowOfData[4]);
				pQuantity = Integer.parseInt(oneRowOfData[5]);

				product_error = mc.AddProduct(pName, pWeight, pVolume, pShelf, pPrice, pQuantity);
				if(!product_error){
					//lol
					break;
				}


			}
			System.out.println(clientName+" "+clientAddress+" "+pName+" "+pWeight+" "+pVolume+" "+pShelf+" "+pPrice+" "+pQuantity);
		}
		input.close();
	}

}
