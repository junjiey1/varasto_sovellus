package vPakkaus.Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    
    private Connection conn = null;
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

    // ei toimi vielä
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

    	/////////////////////////////////////

    	PreparedStatement haetiedot=null;
		ResultSet rs = null;
		String pass="";
		
		String passiwordi = null;
		
		
		if (uname.equals("grigorij")){
			passiwordi = "pass";
		}
		else if (uname.equals("julle")){
			passiwordi = "juliusw";
		}
		else if (uname.equals("teemu")){
			passiwordi = "teemu";
		}
		else if (uname.equals("ben")){
			passiwordi = "root";
		}
		

    	try{
			Class.forName("com.mysql.jdbc.Driver");
			conn =DriverManager.getConnection("jdbc:mysql://localhost/varasto", "root", passiwordi);
		} catch (SQLException e) {
			System.out.println("Yhteyden muodostaminen epäonnistui");
	    } catch (ClassNotFoundException e){
			System.out.println("JDBC-ajurin lataus epäonnistui");
		}
		try{
			haetiedot = conn.prepareStatement("SELECT * FROM users WHERE user = ?");
			try {
				haetiedot.setString(1, uname);
				rs = haetiedot.executeQuery();
				try {
					while(rs.next()){
						pass = rs.getString("pass");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally{
					rs.close();
					System.out.println("Tulosjuokko suljettu");
				}
			}catch(SQLException e){
				System.out.println("Haku " + uname + " epäonnistui!");
				e.printStackTrace();
			}
		}catch(Exception e){
			System.out.println("Tietojen haku epäonnistui!");
		}finally{
			try {
				rs.close();
				haetiedot.close();
				conn.close();
				System.out.println("Haku kysely suljettu");
			} catch (SQLException e) {
				System.out.println("Yhteyden sulkemisessa vikaa");
				e.printStackTrace();
			}
		}

    	/////////////////////////////////////

		if(pass.equalsIgnoreCase(pword))
			System.out.println("LOG IN ONNISTUI : " + uname);
    	//user = uname;
    }
}
