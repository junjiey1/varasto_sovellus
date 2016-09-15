package vPakkaus.Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
