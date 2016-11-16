package vPakkaus.Controllers;

import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import vPakkaus.Asiakas;

public class AsiakasControlleri implements Nakyma_IF {

	@FXML
	private TextField customerName, customerStreet, customerPostalCode, customerCity, customerState,
			contactPersonFname, contactPersonLname, contactPersonEmail, contactPersonPhone;

	private String Customer_Name, Customer_Street, Customer_Postal, Customer_City, Customer_State,
			ContactP_F_Name, ContactP_L_Name, ContactP_Email, ContactP_Phone, postalNumber;

	private MainController_IF mc;
	private NayttojenVaihtaja_IF vaihtaja;
	private Asiakas a;
	private boolean muokataanOlemassaOlevaa = false;

	@Override
	public void setMainController(MainController_IF m) {
		mc = m;
	}

	@Override
	public void paivita(Object data) {
		if(data==null)
			return;
		if(data instanceof Asiakas){
			a = (Asiakas) data;
			muokataanOlemassaOlevaa = true;
			customerName.setText(a.getNimi());
			customerStreet.setText(a.getOsoit());
			customerPostalCode.setText(a.getPosnumero());
			customerCity.setText(a.getKaupun());
			contactPersonFname.setText("");
			contactPersonEmail.setText(a.getEmai());
			contactPersonPhone.setText(a.getNumero());
		}else
			JOptionPane.showMessageDialog(null, data.toString(), "ILMOITUS",
					JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void resetoi() {
		customerName.setText("");
		customerStreet.setText("");
		customerPostalCode.setText("");
		customerCity.setText("");
		contactPersonFname.setText("");
		contactPersonLname.setText("");
		contactPersonEmail.setText("");
		contactPersonPhone.setText("");
		muokataanOlemassaOlevaa = false;
	}

	@Override
	public void virheIlmoitus(Object viesti) {
		JOptionPane.showMessageDialog(null, viesti.toString(), "ILMOITUS",
				JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void esiValmistelut() {
		resetoi();
	}

	@Override
	public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
		vaihtaja.rekisteröiNakymaKontrolleri(this, "customer");
		this.vaihtaja = vaihtaja;
	}

	public void back(){//Button Callback funktio
		vaihtaja.asetaUudeksiNaytoksi("customerview", "Asiakkaat",null);
	}

	public boolean confirmation(){
		return true;
	}

	public void saveChanges() { //Button Callback funktio
		if (parseData()){
			if(!muokataanOlemassaOlevaa){
				a = new Asiakas(Customer_Name, Customer_Street,Customer_City,
					ContactP_Email, ContactP_Phone,postalNumber
				);
				mc.TallennaAsiakas(a);
				vaihtaja.asetaUudeksiNaytoksi("customer", "ASIAKAS : " + a.getNimi() ,a);
				//muokataanOlemassaOlevaa = true;
			}else{
				a.setNimi(Customer_Name);
				a.setKaupun(Customer_City);
				a.setPosnumero(postalNumber);
				a.setOsoit(Customer_Street);
				a.setNumero(ContactP_Phone);
				a.setEmai(ContactP_Email);
				mc.updateAsiakas(a);
			}
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
		if(customerPostalCode.getText().isEmpty()){
			showError("Postal number field", "is empty");
			return false;
		}else
			postalNumber = customerPostalCode.getText();

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
				ContactP_Phone = contactPersonPhone.getText();
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
