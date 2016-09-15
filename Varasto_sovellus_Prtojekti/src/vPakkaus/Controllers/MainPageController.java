package vPakkaus.Controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import vPakkaus.MainLaunch;

public class MainPageController {

    @FXML
    private Tab addProductTab;
    @FXML
    private Tab tab3;
    @FXML
    private Tab tab4;
    @FXML
    private Tab tab5;
    @FXML
    private  Label currentUserLbl;
   
    Tab activeTab;
    String resource;
    
    
    public void initialize(){
    	currentUserLbl.setText("CURRENT USER :  " + LoginController.user);
    }
    
    
    public void tabChoose() throws IOException{
    	
    	System.gc(); // CLEAR MEMORY
    	
    	if (addProductTab.isSelected()) {
    		activeTab = addProductTab;
    		resource = "view/addProduct.fxml";
    	}
    	if (tab3.isSelected()) {
    		activeTab = tab3;
    		resource = "view/addProduct.fxml";
    	}
    	if (tab4.isSelected()) {
    		activeTab = tab4;
    		resource = "view/addProduct.fxml";
    	}
    	if (tab5.isSelected()) {
    		activeTab = tab5;
    		resource = "view/addProduct.fxml";
    	}
    	MainLaunch.windowConstructor(resource, "VarastoSovellus 1.01", activeTab);
    }

	
	public void logOut() throws IOException{
		MainLaunch.windowDestroyer();
		MainLaunch.windowConstructor("view/LoginView.fxml", "LOG IN", null);
	}
}
