package vPakkaus.Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;

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
    

    public void addNewProduct(){

    	//DB_AccessObject.Lisaa(productName.getText(), paino, tilavuus, hyllypaikka, saapumispaiva, lahtopaiva, hinta, lisaaja_id, poistaja_id, maara)

    }

    
    @FXML
    public void handleFilesDragDropped(DragEvent event) throws FileNotFoundException {
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
        	path = db.getFiles().toString();
        	path = path.substring(1, path.length()-1);
        	event.consume();
        	readFromFile(path);
        }
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
