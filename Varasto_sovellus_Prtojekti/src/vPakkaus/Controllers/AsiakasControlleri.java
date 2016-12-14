package vPakkaus.Controllers;

import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import vPakkaus.Asiakas;

/**
 *
 * @author Julius
 *
 *Asiakaan lisäys/päivitys ikkunan kontrolleri.
 */

public class AsiakasControlleri implements Nakyma_IF {

	@FXML
	private TextField customerName, customerStreet, customerPostalCode, customerCity, customerState,
			contactPersonFname, contactPersonLname, contactPersonEmail, contactPersonPhone;

	private String Customer_Name, Customer_Street, Customer_City,
			  ContactP_Email, ContactP_Phone, postalNumber, ContactP_F_Name, ContactP_L_Name;

	private MainController_IF mc;
	private NayttojenVaihtaja_IF vaihtaja;
	private Asiakas a;
	private boolean muokataanOlemassaOlevaa = false;

	public AsiakasControlleri(){
	  vaihtaja=null;
	  mc=null;
	}

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
		}else{
//			JOptionPane.showMessageDialog(null, data.toString(), "ILMOITUS",
//					JOptionPane.INFORMATION_MESSAGE);
		  Alert info = new Alert(AlertType.INFORMATION);
	    info.setTitle("Ilmoitus");
	    info.setHeaderText("Asiakas lisääminen");
	    info.setContentText(data.toString());
	    info.showAndWait();
		}
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
	  if(!muokataanOlemassaOlevaa)
	    resetoi();
	}

	@Override
	public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
		vaihtaja.rekisteröiNakymaKontrolleri(this, "customer");
		this.vaihtaja = vaihtaja;
	}

	/**
	 * Back napin callback-funktio, joka asettaa uuden näkymän
	 * */
	public void back(){//Button Callback funktio
	  resetoi();
		vaihtaja.asetaUudeksiNaytoksi("customerview", "Asiakkaat",null);
	}

	/**
	 * Funktio, joka kysyy käyttäjältä vahvistusta asiakkaan tallennuksesta/päivityksestä
	 * */
	public boolean confirmation(){
		return true;
	}

	/**
	 * Funktio, joka tallentaa näkymän tekstikenttien tiedot tietokantaan
	 * */
	public void saveChanges() { //Button Callback funktio
		if (parseData()){
			if(!muokataanOlemassaOlevaa){
				a = new Asiakas(Customer_Name, Customer_Street,Customer_City,
					ContactP_Email, ContactP_Phone,postalNumber
				);
				mc.tallennaAsiakas(a);
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


	/**
	 * Funktio, joka validoi näkymän tekstikenttien sisällöt.
	 * @return true, jos tekstikentät oikein täytetty
	 * @return false, jos tekstikentät väärin täytetty
	 * */
	private boolean parseData(){

		if (customerName.getText().isEmpty()){
			showError("Customer name field", "is empty");
			return false;
		}else{
			Customer_Name = customerName.getText();
		}

		if (customerStreet.getText().isEmpty()){
			showError("Customer street address field", "is empty");
			return false;
		}else{
			Customer_Street = customerStreet.getText();
		}

		if(customerPostalCode.getText().isEmpty()){
      showError("Postal number field", "is empty");
      return false;
    }else{
      postalNumber = customerPostalCode.getText();
    }

		if (customerCity.getText().isEmpty()){
			showError("Customer city field", "is empty");
			return false;
		}else{
			Customer_City = customerCity.getText();
		}

		if (contactPersonFname.getText().isEmpty()){
			showError("Contact person first name field", "is empty");
			return false;
		}else{
			ContactP_F_Name = contactPersonFname.getText();
		}

		if (contactPersonLname.getText().isEmpty()){
			showError("Contact person last name field", "is empty");
			return false;
		}else{
			ContactP_L_Name = contactPersonLname.getText();
		}

		if (contactPersonEmail.getText().isEmpty()){
			showError("Contact person email field", "is empty");
			return false;
		}else{
			if (contactPersonEmail.getText().contains("@") &&
				contactPersonEmail.getText().contains(".") &&
				contactPersonEmail.getText().lastIndexOf(".") > contactPersonEmail.getText().indexOf("@")){

				ContactP_Email = contactPersonEmail.getText();
			}else{
				showError("Contact person email", "should be in following format: example@exa.com");
				return false;
			}
		}

		if (contactPersonPhone.getText().isEmpty()){
			showError("Contact person phone field", "is empty");
			return false;
		}else{
			if(contactPersonPhone.getText().replaceAll(" ", "").length()<13){
				showError("Contact person phone number", "should be in following format: +123 12 345 6789");
				return false;
			}else{
				ContactP_Phone = contactPersonPhone.getText();
				Customer_Name = customerName.getText();
			}
		}

		return true;
	}


	/**
	 *
	 * @param String element
	 * @param String msg
	 * Private funktio, joka näyttää virheviestin käyttäjälle
	 */
	private void showError(String element, String msg){
//		JOptionPane.showMessageDialog(null, element +" "+msg, element,
//				JOptionPane.INFORMATION_MESSAGE);
    Alert error = new Alert(AlertType.ERROR);
    error.setTitle("Virhe ilmoitus");
    error.setHeaderText("Virhe");
    error.setContentText(element +" "+msg);
    error.showAndWait();
	}

}
