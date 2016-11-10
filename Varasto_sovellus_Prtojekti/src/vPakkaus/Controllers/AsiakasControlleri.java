package vPakkaus.Controllers;

import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AsiakasControlleri implements Nakyma_IF {

	@FXML
	private TextField customerName, customerStreet, customerPostalCode, customerCity, customerState,
			contactPersonFname, contactPersonLname, contactPersonEmail, contactPersonPhone;

	
	String Customer_Name, Customer_Street, Customer_Postal, Customer_City, Customer_State,
			ContactP_F_Name, ContactP_L_Name, ContactP_Email, ContactP_Phone;

	@Override
	public void setMainController(MainController_IF m) {
		
	}

	@Override
	public void paivita(Object data) {
		
	}

	@Override
	public void resetoi() {
		
	}

	@Override
	public void virheIlmoitus(Object viesti) {
		
	}

	@Override
	public void esiValmistelut() {
		
	}

	@Override
	public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {

	}

	public void saveChanges() {
		if (parseData()){
			System.out.println("WORKINGS");
			//Save data to DB
			
			
		}
		
	}
	
	private boolean parseData(){
		boolean allGood = true;
		
		if (customerName.getText().isEmpty()){
			allGood = false;
			showError("Customer name field", "is empty");
		}else{
			Customer_Name = customerName.getText();
		}
		if (customerStreet.getText().isEmpty()){
			allGood = false;
			showError("Customer street address field", "is empty");
		}else{
			Customer_Street = customerStreet.getText();
		}
		if (customerCity.getText().isEmpty()){
			allGood = false;
			showError("Customer city field", "is empty");
		}else{
			Customer_City = customerCity.getText();
		}
		if (contactPersonFname.getText().isEmpty()){
			allGood = false;
			showError("Contact person first name field", "is empty");
		}else{
			ContactP_F_Name = contactPersonFname.getText();
		}
		if (contactPersonLname.getText().isEmpty()){
			allGood = false;
			showError("Contact person last name field", "is empty");
		}else{
			ContactP_L_Name = contactPersonLname.getText();
		}
		if (contactPersonEmail.getText().isEmpty()){
			allGood = false;
			showError("Contact person email field", "is empty");
		}else{
			if (contactPersonEmail.getText().contains("@") && 
				contactPersonEmail.getText().contains(".") && 
				contactPersonEmail.getText().lastIndexOf(".") > contactPersonEmail.getText().indexOf("@")){
				
				ContactP_Email = contactPersonEmail.getText();
			}else{
				allGood = false;
				showError("Contact person email", "should be in following format: example@exa.com");
			}
		}
		if (contactPersonPhone.getText().isEmpty()){
			allGood = false;
			showError("Contact person phone field", "is empty");
		}else{
			if(contactPersonPhone.getText().replaceAll(" ", "").length()<13){
				allGood = false;
				showError("Contact person phone number", "should be in following format: +123 12 345 6789");
			}else{
				Customer_Name = customerName.getText();
			}
		}
		
		return allGood;
	}

	private void showError(String element, String msg){
		JOptionPane.showMessageDialog(null, element +" "+msg, element,
				JOptionPane.INFORMATION_MESSAGE);
	}

}
