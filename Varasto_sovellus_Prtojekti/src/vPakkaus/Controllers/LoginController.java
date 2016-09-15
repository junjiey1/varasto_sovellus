package vPakkaus.Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.AccessibleRole;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import vPakkaus.DB_AccessObject;
import vPakkaus.MainLaunch;

public class LoginController {

	@FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private CheckBox showpword;
    @FXML
    private Label incorrectLabel;

    private Connection conn = null;
    String uname, pword;
    boolean allGood;

    private static String user = null;
    private static int userID = 0;

    public LoginController()
    {
    	System.out.println("LOG IN CONTROLLER");
    }

    public void initialize(){
    	incorrectLabel.setVisible(false);
    }


    public void login() throws IOException{
    	allGood = true;
    	incorrectLabel.setVisible(false);
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

    // ei toimi vielä
    public void showpword(){
    	if (showpword.isSelected()){
    		passwordTxt.setAccessibleRole(AccessibleRole.TEXT_FIELD);
    	}
    	else{
    		passwordTxt.setAccessibleRole(AccessibleRole.PASSWORD_FIELD);
    	}
    }


    public void checkUnamePword(String uname, String pword) throws IOException{

    	int[] list = DB_AccessObject.LogIn(uname, pword);//palauttaa listan[true/false,id]

    	//Connect to DB and check uname & pword pair.
    	System.out.println(uname);
    	System.out.println(pword);
		if(list[0] == 1){
			System.out.println("LOG IN ONNISTUI : " + uname);
			user = uname;
			userID = list[1];
			MainLaunch.windowDestroyer();
			MainLaunch.windowConstructor("view/MainPageView.fxml", "VarastoSovellus 1.01", null);
		}else{
			incorrectLabel.setVisible(true);
			passwordTxt.setText("");
			usernameTxt.requestFocus();
		}
    }

    public static int getID()
    {
    	return userID;
    }

    public static String getName()
    {
    	return user;
    }
}
