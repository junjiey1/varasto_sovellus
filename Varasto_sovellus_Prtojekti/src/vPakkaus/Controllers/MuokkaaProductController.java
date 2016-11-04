package vPakkaus.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
public class MuokkaaProductController implements SetMainController {

	@FXML
	private TextField productName;
	@FXML
	private TableView<Product> tuoteTable;
	@FXML
	private TableColumn<Product, Integer> maaraCol;
	@FXML
	private TableColumn<Product, String> nameCol;
	@FXML
	private TableColumn<Product, Double> weightCol;
	@FXML
	private TableColumn<Product, Float> priceCol;
	@FXML
	private TableColumn<Product, Double> volumeCol;

	private MainController_IF mc;
	private ArrayList<Product> p;
	ObservableList<String> productTextFiles = FXCollections.observableArrayList();
	ObservableList<Product> tuote = FXCollections.observableArrayList();
	boolean hae;
	private Product[] PaivitettavatTuotteet;

	public void initialize() {
		p = new ArrayList();
		tuoteTable.setEditable(true);
		Callback<TableColumn<Product, Integer>, TableCell<Product, Integer>> cellFactory = new Callback<TableColumn<Product, Integer>, TableCell<Product, Integer>>() {
			public TableCell call(TableColumn p) {
				return new EditingCell(1,PaivitettavatTuotteet);
			}
		};
		Callback<TableColumn<Product, String>, TableCell<Product, String>> cellFactory2 = new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {
			public TableCell call(TableColumn p) {
				return new EditingCell(2,PaivitettavatTuotteet);
			}
		};
		Callback<TableColumn<Product, Double>, TableCell<Product, Double>> cellFactory3 = new Callback<TableColumn<Product, Double>, TableCell<Product, Double>>() {
			public TableCell call(TableColumn p) {
				EditingCell e = new EditingCell(3,PaivitettavatTuotteet);
				return e;
			}
		};

		Callback<TableColumn<Product, Float>, TableCell<Product, Float>> cellFactory4 = new Callback<TableColumn<Product, Float>, TableCell<Product, Float>>() {
			public TableCell call(TableColumn p) {
				return new EditingCell(4,PaivitettavatTuotteet);
			}
		};
		maaraCol.setCellFactory(cellFactory);
		nameCol.setCellFactory(cellFactory2);
		weightCol.setCellFactory(cellFactory3);
		volumeCol.setCellFactory(cellFactory3);
		priceCol.setCellFactory(cellFactory4);
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
		tuoteTable.refresh(); // Varmuuden vuoksi päivitetään TableView
	}

	public void täytäTaulukko(ArrayList<Product> lista) {
		try {
			for (Product pro : p) {
				if (p == null)
					continue;
				maaraCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("maara"));
				nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("product_name"));
				weightCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("product_weight"));
				priceCol.setCellValueFactory(new PropertyValueFactory<Product, Float>("product_price"));
				volumeCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("product_volume"));
				tuoteTable.getItems().add(pro);
			}
		} catch (Exception e) {
			System.out.println("virhe taulukon täytössä");
			e.printStackTrace();
		}
	}

	public void SearchManually() throws IOException {
		hae = true;
		if (productName.getText().isEmpty()) {
			hae = false;
		}
		if (hae) {
			HaeTuoteet();
			System.out.println("P " + p.size());
			PaivitettavatTuotteet = new Product[p.size()]; // Luo uusi lista
															// jonne voidaan
															// tallentaa
															// muokattuja
															// tuotteita
			Reset();
			if (p == null) // Saatu tuote lista on null eli tyhjä

				return; // error viesti tÃ¤nne ku ei lÃ¶ytynyt mitÃ¤Ã¤n
			täytäTaulukko(p);
		} else {
			System.out.println("KenttÃ¤ on tÃ¤ytetty vÃ¤Ã¤rÃ¤llÃ¤ tavalla");
		}
	}

	@Override
	public void setMainController(MainController_IF m) {
		mc = m;
	}

	public void HaeTuoteet() {
		p = mc.haeTuote(productName.getText());
	}

	private boolean isEmpty() {
		for (Product p : PaivitettavatTuotteet) {
			if (p != null)
				return false;
		}
		return true;
	}

	private ArrayList<Product> convertToArrayList() {
		ArrayList<Product> res = new ArrayList<Product>();
		for (Product p : PaivitettavatTuotteet) {
			if (p != null)
				res.add(p);
		}
		return res;
	}

	public void paivitaTuotteet() {
		if (PaivitettavatTuotteet == null || isEmpty()) // Tuote lista on tyhjä käyttäjä ei oo muokannut tuotteita
			return;
		System.out.println("Tyhjä? " + isEmpty());
		if (0 == JOptionPane.showConfirmDialog(null, "Punaisella merkityt rivit tallenetaan pysyvästi\njatketaanko?")) {
			if (mc.paivitaTuotteet(convertToArrayList())) {
				JOptionPane.showMessageDialog(null, "Tiedot päivitetty onnistuneesti", "Päivitys onnistui",
						JOptionPane.INFORMATION_MESSAGE);
				PaivitettavatTuotteet = new Product[p.size()];
				// Luodaan uusi
				// tyhjä
				// päivitys
				// lista
				// edellisen
				// päälle
			} else
				JOptionPane.showMessageDialog(null, "Tietokantaa ei voitu päivittää!",
						"Virhe havaittu tietokannan päivityksessä", JOptionPane.ERROR_MESSAGE);
			Reset();
			täytäTaulukko(p);
		}
	}

}