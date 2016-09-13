package vPakkaus.Controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import vPakkaus.MainLaunch;

public class MainPageController {
	
	
//	  @FXML
//    private Button addProduct;
//    @FXML
//    private Button removeProduct;
//    @FXML
//    private Button moveProduct;
//    @FXML
//    private Button logOut;
//    @FXML
//    private Button warehouseHistory;
//    @FXML
//    private Button showGraphs;
//    @FXML
//    private Button addUser;
    
    
    
    public void addProduct(){
    	
    }
    
    
	public void removeProduct(){
	    	
	}
	
	
	public void moveProduct(){
		
	}
	
	
	
	public void warehouseHistory(){
		
	}
	
	
	public void showGraphs(){
			
	}
	
	
	public void addUser(){
		
	}

	
	public void logOut() throws IOException{
		MainLaunch.windowDestroyer();
		MainLaunch.windowConstructor("view/LoginView.fxml", "LOG IN");
	}

}
