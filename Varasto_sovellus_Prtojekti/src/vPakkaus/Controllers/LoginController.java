package vPakkaus.Controllers;

import javafx.fxml.FXML;
import javafx.scene.AccessibleRole;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	
	@FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private CheckBox showpword;
    
    
    String uname, pword;
    boolean allGood;
    
    static String user = null;
       
    
    public void login(){
    	allGood = true;
    	if(usernameTxt.getText().isEmpty()){
    		allGood = false;
    	}
    	
    	if(passwordTxt.getText().isEmpty()){
    		allGood = false;
    	}
    	
    	if (allGood){
    		uname = usernameTxt.getText();
    		pword = passwordTxt.getText();
    		
    		checkUnamePword(uname, pword);
    	}
    }
    
    
    public void showpword(){
    	if (showpword.isSelected()){
    		passwordTxt.setAccessibleRole(AccessibleRole.TEXT_FIELD);
    	}
    	else{
    		passwordTxt.setAccessibleRole(AccessibleRole.PASSWORD_FIELD);
    	}
    }
    
    
    public void checkUnamePword(String uname, String pword){
    	
    	//Connect to DB and check uname & pword pair.
    	System.out.println(uname);
    	System.out.println(pword);
    	
    	user = uname;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
