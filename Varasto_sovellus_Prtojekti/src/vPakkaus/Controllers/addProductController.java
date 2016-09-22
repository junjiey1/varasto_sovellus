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
    private MainController mc;


    boolean allGood;

    File file;
    Scanner input;
    String[] oneRowOfData;
    String path;

    public void setMainController(MainController m)
    {
    	mc = m;
    }

    public void AddProductManually() throws IOException{
    	allGood = true;

    	if(productName.getText().isEmpty() || quantity.getText().isEmpty() || price.getText().isEmpty() || weight.getText().isEmpty() || volume.getText().isEmpty() || whLocation.getText().isEmpty()){
    		allGood = false;
    	}

    	try{
    	    Integer.parseInt(quantity.getText());
    	    Double.parseDouble(volume.getText());
    	    Double.parseDouble(weight.getText());
    	    Float.parseFloat(price.getText());
    	}catch (NumberFormatException ex) {
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
    		int lisaajan_id = mc.getID();

    		Product product = new Product(productName.getText(), whLocation.getText(), Double.parseDouble(weight.getText()), Double.parseDouble(volume.getText()), Float.parseFloat(price.getText()));
    		AddProducts add = new AddProducts(product, Integer.parseInt(quantity.getText()), lisaajan_id, saapumispaiva, lahtopaiva);

    		addNewProduct(add);//if -> ilmoitus, että onnistui

    	} else {
    		System.out.println("joku kenttä on tyhjä tai väärin täytetty");
    	}
    }

    public boolean addNewProduct(AddProducts add){
    	return mc.AddProduct(add.getProduct().getProduct_name(), add.getProduct().getProduct_weight(), add.getProduct().getProduct_volume(), add.getProduct().getProduct_location(), add.getSaapumispaiva(), add.getLahtopaiva(), add.getProduct().getProduct_price(), add.getAdd_user_id(), add.getProduct_quantity());

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
