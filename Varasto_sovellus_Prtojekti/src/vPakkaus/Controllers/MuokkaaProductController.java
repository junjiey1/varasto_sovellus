package vPakkaus.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

	private int mode;
	private MainController_IF mc;
	private ArrayList<DAO_Objekti> p = new ArrayList<DAO_Objekti>();
	boolean hae;
	private NayttojenVaihtaja_IF vaihtaja;
	private TaulukkoFactory tehdas;
	private Taulukko_IF taulukko;


	public void initialize() {
		mode = 1;
		hyllyButton.setStyle("-fx-font: 13 arial; -fx-base: #b6e7c9;");//F75757
		tuoteButton.setStyle("-fx-font: 13 arial; -fx-base: #F75757;");
		switchMode();
		tuoteTable.setEditable(true);
		tehdas = new TaulukkoFactory();
	}

	public void Reset() {
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

	private void luoUusiTaulukko(){
		if(p.size()<=0)
			return;
		tuoteTable.getColumns().clear();
		taulukko = tehdas.annaTaulukko(p.get(0), p);
		tuoteTable.getColumns().addAll(taulukko.getTaulukko().getColumns());
	}

	public void SearchManually() throws IOException {
		hae = true;
		if (productName.getText().isEmpty()) {
			hae = false;
		}
		if (hae) {
			Reset();
			HaeTuoteet();
			luoUusiTaulukko();
			täytäTaulukko();
			if (p == null) // Saatu tuote lista on null eli tyhjä
				return; // error viesti tÃ¤nne ku ei lÃ¶ytynyt mitÃ¤Ã¤n
		} else {
			System.out.println("KenttÃ¤ on tÃ¤ytetty vÃ¤Ã¤rÃ¤llÃ¤ tavalla");
		}
	}

	@Override
	public void setMainController(MainController_IF m) {
		mc = m;
	}

	public void HaeTuoteet() {
		p.addAll(mc.haeTuote(productName.getText()));
	}

	public void paivitaTuotteet() {
		if (0 == JOptionPane.showConfirmDialog(null, "Punaisella merkityt rivit tallenetaan pysyvästi\njatketaanko?")) {
			if (taulukko.paivitaTietokantaan(mc, this)) {
				JOptionPane.showMessageDialog(null, "Tiedot päivitetty onnistuneesti", "Päivitys onnistui",
						JOptionPane.INFORMATION_MESSAGE);
			}else{
				JOptionPane.showMessageDialog(null, "Tietokantaa ei voitu päivittää!",
					"Virhe havaittu tietokannan päivityksessä", JOptionPane.ERROR_MESSAGE);
			}
			Reset();
			täytäTaulukko();
		}
	}

	public void switchMode(){
		switch(mode){//F75757
			case 1:
				mode = 2;
				tuoteButton.setDisable(true);
				hyllyButton.setDisable(false);
				hyllyButton.setStyle("-fx-font: 13 arial; -fx-base: #b6e7c9;");//F75757
				tuoteButton.setStyle("-fx-font: 13 arial; -fx-base: #F75757;");
				break;
			case 2:
				mode = 1;
				hyllyButton.setDisable(true);
				tuoteButton.setDisable(false);
				tuoteButton.setStyle("-fx-font: 13 arial; -fx-base: #b6e7c9;");//F75757
				hyllyButton.setStyle("-fx-font: 13 arial; -fx-base: #F75757;");
		}
	}

	@Override
	public void paivita(Object data) {

	}

	@Override
	public void resetoi() {
		// TODO Auto-generated method stub

	}

	@Override
	public void virheIlmoitus(Object viesti) {
		JOptionPane.showMessageDialog(null, viesti.toString(), null, JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void setNaytonVaihtaja(NayttojenVaihtaja_IF vaihtaja) {
		this.vaihtaja = vaihtaja;
	}

	@Override
	public void esiValmistelut() {
	}

}