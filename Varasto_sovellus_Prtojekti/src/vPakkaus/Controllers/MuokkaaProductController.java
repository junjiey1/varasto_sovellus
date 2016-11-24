package vPakkaus.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import vPakkaus.DAO_Objekti;
import vPakkaus.Product;


/**
*Tuoteen muokkaus tabi ikkunan kontrolleri. Vastaa taulukon luonnista.
*/
public class MuokkaaProductController implements Nakyma_IF {

	@FXML
	private TextField productName;
	@FXML
	private TableView<DAO_Objekti> tuoteTable;
	@FXML
	private Button hyllyButton;
	@FXML
	private Button tuoteButton;
	@FXML
	private Label label1;

	private int mode;
	private MainController_IF mc;
	private ArrayList<DAO_Objekti> p;
	boolean hae;
	private NayttojenVaihtaja_IF vaihtaja;
	private TaulukkoFactory tehdas;
	private Taulukko_IF taulukko;




	public MuokkaaProductController(){
	  vaihtaja = null;
	  mc = null;
	  p = new ArrayList<DAO_Objekti>();
	}

	public void initialize() {
		mode = 1;
		hyllyButton.setStyle("-fx-font: 13 arial; -fx-base: #b6e7c9;");//F75757
		tuoteButton.setStyle("-fx-font: 13 arial; -fx-base: #F75757;");
		switchMode();
		tuoteTable.setEditable(true);
		tehdas = new TaulukkoFactory();
	}

	public void reset() {
		int length = tuoteTable.getItems().size(); // Hae taulun rivien määrä
		if (length > 0) {// Jos on rivejä
			for (; 0 < length;) {// Poistetaan yksi kerrallaan
				System.out.println("Deleting");
				tuoteTable.getItems().remove(0);
				length--;
			}
		}
		p = new ArrayList<DAO_Objekti>();
		tuoteTable.refresh(); // Varmuuden vuoksi päivitetään TableView
	}

	private void täytäTaulukko() {
		tuoteTable.getItems().addAll(taulukko.getTaulukko().getItems());
		tuoteTable.refresh();
	}

	private boolean luoUusiTaulukko(){
		if(p == null || p.size()<=0)
			return false;
		tuoteTable.getColumns().clear();
		taulukko = tehdas.annaTaulukko(p.get(0), p);
		tuoteTable.getColumns().addAll(taulukko.getTaulukko().getColumns());
		return true;
	}

	public void searchManually() throws IOException {
		hae = true;
		if (productName.getText().isEmpty()) {
			hae = false;
		}
		if (hae) {
			reset();
			haeKohteetTietokannasta();
			if(luoUusiTaulukko()){
				täytäTaulukko();
			}else{
				virheIlmoitus("Annetulla hakusanalla ei löytynyt tuotteita");
				return;
			}
		} else {
			System.out.println("KenttÃ¤ on tÃ¤ytetty vÃ¤Ã¤rÃ¤llÃ¤ tavalla");
		}
	}

	@Override
	public void setMainController(MainController_IF m) {
		mc = m;
	}

	public void haeKohteetTietokannasta() {
	  if(mode==2)
	    p.addAll(mc.haeTuote(productName.getText()));
	  else if(mode==1)
	    p.addAll(mc.haeHyllypaikka(productName.getText()));
	}

	public void paivitaTuotteet() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("CONFIRM");
		alert.setHeaderText("Punaisella merkityt rivit tallenetaan pysyvästi\njatketaanko");
		alert.setContentText("Jatketaanko?");
		ButtonType buttonTypeOne = new ButtonType("Kyllä");
		ButtonType buttonTypeTwo = new ButtonType("Ei", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne){
			if(taulukko.paivitaTietokantaan(mc, this)){
  			Alert info = new Alert(AlertType.INFORMATION);
  			info.setTitle("Päivitys onnistuu");
  			info.setHeaderText("Päivitys onnistui");
  			info.setContentText("Tiedot päivitetty onnistuneesti");
  			info.showAndWait();
  			reset();
        täytäTaulukko();
  		} else{
  			Alert huomautus = new Alert(AlertType.ERROR);
  			huomautus.setTitle("Error");
  			huomautus.setHeaderText("Tietokantaa ei voitu päivittää!");
  			huomautus.setContentText("Virhe havaittu tietokannan päivityksessä");
  			huomautus.showAndWait();
  		}
		}
	}

	public void switchMode(){
		switch(mode){//F75757
			case 1:
				mode = 2;
				label1.setText("Etsitään tuotteita");
				productName.setPromptText("Tuotteen nimi");
				tuoteButton.setStyle("-fx-font: 13 arial; -fx-base: #b6e7c9;");//F75757
        hyllyButton.setStyle("-fx-font: 13 arial; -fx-base: #F75757;");
				break;
			case 2:
				mode = 1;
				label1.setText("Etsitään hyllypaikkoja");
				productName.setPromptText("Hyllypaikan nimi");
				hyllyButton.setStyle("-fx-font: 13 arial; -fx-base: #b6e7c9;");//F75757
        tuoteButton.setStyle("-fx-font: 13 arial; -fx-base: #F75757;");
		}
	}

	@Override
	public void paivita(Object data) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("TIEDOTE");
		alert.setContentText(data.toString());
		ButtonType buttonTypeOne = ButtonType.OK;
		alert.getButtonTypes().setAll(buttonTypeOne);
		alert.showAndWait();
	}

	@Override
	public void resetoi() {

	}

	@Override
	public void virheIlmoitus(Object viesti) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText(viesti.toString());
		alert.showAndWait();
	}


	@Override
	public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
		this.vaihtaja = vaihtaja;
		vaihtaja.rekisteröiNakymaKontrolleri(this, "searchpage");
	}

	@Override
	public void esiValmistelut() {
	}

}