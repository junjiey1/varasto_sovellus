package vPakkaus.Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import vPakkaus.AddProducts;
import vPakkaus.CurrentDate;
import vPakkaus.DB_AccessObject;
import vPakkaus.Product;

public class addProductController {

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


    boolean allGood;

    File file;
    Scanner input;
    String[] oneRowOfData;
    String path;

    public void fieldChecksAddProduct() throws IOException{
    	allGood = true;

    	if(productName.getText().isEmpty() || quantity.getText().isEmpty() || price.getText().isEmpty() || weight.getText().isEmpty() || volume.getText().isEmpty() || whLocation.getText().isEmpty()){
    		allGood = false;
    	}

    	if (allGood){

    		CurrentDate curdate = new CurrentDate();
    		String[] split = curdate.getCurrentDate();
    		int year = Integer.parseInt(split[2]);
    		int month = Integer.parseInt(split[1]);
    		int day = Integer.parseInt(split[0]);

    		Date saapumispaiva = new Date(year-1900, month-1, day);
    		Date lahtopaiva = null;
    		int add_user_id = 1;

    		Product product = new Product(productName.getText(), whLocation.getText(), Double.parseDouble(weight.getText()), Double.parseDouble(volume.getText()), Float.parseFloat(price.getText()));
    		AddProducts add = new AddProducts(product, Integer.parseInt(quantity.getText()), add_user_id, saapumispaiva, lahtopaiva);
    		System.out.println(add.getProduct().getProduct_name());
    		System.out.println(saapumispaiva);
    		addNewProduct(add.getProduct().getProduct_name(), add.getProduct().getProduct_weight(), add.getProduct().getProduct_volume(), add.getProduct().getProduct_location(), add.getSaapumispaiva(), add.getLahtopaiva(), add.getProduct().getProduct_price(), add_user_id, add.getProduct_quantity());
    	}
    }

    public void addNewProduct(String nimi, double paino, double tilavuus, String hyllypaikka, Date saapumispaiva, Date lahtopaiva, float hinta, int lisaaja_id, int maara){

    	DB_AccessObject.Lisaa(nimi, paino, tilavuus, hyllypaikka, saapumispaiva, lahtopaiva, hinta, lisaaja_id, maara);

    }

    @FXML
    public void handleFilesDragDropped(DragEvent event) throws FileNotFoundException {
        Dragboard db = event.getDragboard();

        System.out.println(db.getFiles().size());

        if (db.hasFiles()) {
        	if (db.getFiles().size() > 1){

        		path = db.getFiles().toString();
        		oneRowOfData = path.split(",");

        		for (int i = 0; i < oneRowOfData.length; i++){

        			if (i == oneRowOfData.length-1){
        				oneRowOfData[i] = oneRowOfData[i].substring(1, oneRowOfData[i].length()-1);
        			}else{
        				oneRowOfData[i] = oneRowOfData[i].substring(1, oneRowOfData[i].length());
        			}

        			readFromFile(oneRowOfData[i]);
        		}

        	}else{
        		System.out.println("1 tiedosto");
        	}
        }
        event.consume();
    }


    public void readFromFile(String name) throws FileNotFoundException{

    	file = new File(name);
    	input = new Scanner(file);

    	while(input.hasNext()){
//			oneRowOfData = input.nextLine().split(",");
    		System.out.println(input.nextLine());
    	}
    }





}
