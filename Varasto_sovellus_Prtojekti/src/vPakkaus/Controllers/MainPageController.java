package vPakkaus.Controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import vPakkaus.LanguageUtil;

public class MainPageController implements Nakyma_IF{


  @FXML
  private Tab homeTab;
	@FXML
	private Tab addProductTab;
	@FXML
	private Tab tab3;
	@FXML
	private Tab tab4;
	@FXML
	private Tab tab5;
	@FXML
	private Label currentUserLbl;
	@FXML
	private Button logout;
	@FXML
	private Label bigLabel;
	@FXML
	private Button chooselanguage;

	private MainController_IF mc;
	private Tab activeTab;
	private NayttojenVaihtaja_IF vaihtaja;

	public MainPageController(){
	  mc=null;
	}


	public void tabChoose() throws IOException {

		if (addProductTab.isSelected()) {
			activeTab = addProductTab;
			activeTab.setContent(vaihtaja.getAnchorPane("addpage"));
		}
		if (tab3.isSelected()) {
			activeTab = tab3;
			activeTab.setContent(vaihtaja.getAnchorPane("searchpage"));
		}
		if (tab4.isSelected()) {
			activeTab = tab4;
		}
		if (tab5.isSelected()) {
			activeTab = tab5;
		}
	}

	public void logOut() throws IOException {
		mc.logOut(); // Poistaa tallennetut käyttäjän nimen ja ID:n
		vaihtaja.asetaUudeksiNaytoksi("login", "LOGIN", null);
	}

	public void whManagement(){
		vaihtaja.asetaUudeksiNaytoksi("ManagementMainMenu", "WareHouseManagement", null);
	}

	public void changeLan(){
	  ObservableList<String> options =
	      FXCollections.observableArrayList(
	          "Español",
	          "English",
	          "Suomi"
	      );
	  ChoiceDialog<String> dialog = new ChoiceDialog<>("English", options);
	  dialog.setTitle("Change language");
	  dialog.setHeaderText("Select your language");
	  Optional<String> result = dialog.showAndWait();
	  if(result.isPresent()){
	    if(result.get().equals("Español"))
	      LanguageUtil.setMessagesResource(ResourceBundle.getBundle("languages/MessagesBundle", new Locale("es", "AR")));
	    else if(result.get().equals("Suomi"))
	      LanguageUtil.setMessagesResource(ResourceBundle.getBundle("languages/MessagesBundle", new Locale("fi", "FI")));
	    else
	      LanguageUtil.setMessagesResource(ResourceBundle.getBundle("languages/MessagesBundle", new Locale("en", "US")));
	    saveToLanguageSettings();
	  }
	  esiValmistelut();
	}

	private void saveToLanguageSettings(){
	  Properties prop = new Properties();
	  OutputStream out = null;
	  FileInputStream in = null;
	  try {
	    in = new FileInputStream("src/languages/languageSettings.properties");
	    Properties props = new Properties();
	    props.load(in);
	    in.close();
	    out = new FileOutputStream("src/languages/languageSettings.properties");
	    System.out.println("writing " + LanguageUtil.getCountry());
      prop.setProperty("country", LanguageUtil.getCountry());
      prop.setProperty("language", LanguageUtil.getLanguage());
      prop.store(out, null);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }finally{
      if(in != null)
        try {
          in.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      if(out!=null)
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
	}

	@Override
	public void setMainController(MainController_IF m) {
		mc = m;
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
	public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
		this.vaihtaja = vaihtaja;
		this.vaihtaja.rekisteröiNakymaKontrolleri(this,"mainpage");
	}


	@Override
	public void esiValmistelut() {
		currentUserLbl.setText(LanguageUtil.getMessageFromResource("currentuser") + " " + mc.getName());
		logout.setText(LanguageUtil.getMessageFromResource("logout"));
		bigLabel.setText(LanguageUtil.getMessageFromResource("program_name"));
		chooselanguage.setText(LanguageUtil.getMessageFromResource("chooselanguage"));
		homeTab.setText(LanguageUtil.getMessageFromResource("main"));
		addProductTab.setText(LanguageUtil.getMessageFromResource("addproduct"));
	}
}
