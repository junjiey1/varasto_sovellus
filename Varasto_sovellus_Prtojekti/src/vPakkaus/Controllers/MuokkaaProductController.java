package vPakkaus.Controllers;

import java.io.IOException;
import java.util.ArrayList;
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
import vPakkaus.Product;

public class MuokkaaProductController implements SetMainController {

	@FXML
	private TextField productName;
	@FXML
	private TableView<Product> tuoteTable;
	@FXML
	private TableColumn<Product, Integer> idCol;
	@FXML
	private TableColumn<Product, String> nameCol;
	@FXML
	private TableColumn<Product, Double> weightCol;
	@FXML
	private TableColumn<Product, Float> priceCol;
	@FXML
	private TableColumn<Product, Double> volumeCol;

	private MainController mc;
	private ArrayList<Product> p;
	ObservableList<String> productTextFiles = FXCollections.observableArrayList();
	ObservableList<Product> tuote = FXCollections.observableArrayList();
	boolean hae;

	public void initialize(){
		p = new ArrayList();
		tuoteTable.setEditable(true);
        Callback<TableColumn<Product, Integer>, TableCell<Product, Integer>> cellFactory = new Callback<TableColumn<Product, Integer>, TableCell<Product, Integer>>() {
             public TableCell call(TableColumn p) {
                return new EditingCell(1);
             }
         };
         Callback<TableColumn<Product, String>, TableCell<Product, String>> cellFactory2 = new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {
             public TableCell call(TableColumn p) {
                return new EditingCell(2);
             }
         };
         Callback<TableColumn<Product, Double>, TableCell<Product, Double>> cellFactory3 = new Callback<TableColumn<Product, Double>, TableCell<Product, Double>>() {
             public TableCell call(TableColumn p) {
            	 EditingCell e = new EditingCell(3);
                return e;
             }
         };

         Callback<TableColumn<Product, Float>, TableCell<Product, Float>> cellFactory4 = new Callback<TableColumn<Product, Float>, TableCell<Product, Float>>() {
             public TableCell call(TableColumn p) {
                return new EditingCell(4);
             }
         };

	    idCol.setCellFactory(cellFactory);
	    idCol.setOnEditCommit(new EventHandler<CellEditEvent<Product, Integer>>() {

            public void handle(CellEditEvent<Product, Integer> t) {
            	Integer i = t.getTableView().getItems().get(t.getTablePosition().getRow()).getID();
                Product p = ((Product) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
                    );
                System.out.println(t.getNewValue().intValue() + " " + p.getID());
                if(t.getNewValue().intValue() != p.getID()){
                	p.setID(t.getNewValue().intValue());
                	System.out.println("Arvoja muutettu Productin arvot nyt : " + p.getID() + p.getProduct_name() + p.getProduct_volume() + p.getProduct_weight() + p.getProduct_price());

                	//t.getTableView().setStyle("-fx-background-color:lightcoral"); //taulukon reuna
                	//t.getTableColumn().setStyle("-fx-background-color:lightcoral"); //kolumni ei rivi
                	//getTableRow().setStyle("-fx-background-color:lightcoral");
                }
            }
         });

	    nameCol.setCellFactory(cellFactory2);
	    nameCol.setOnEditCommit(new EventHandler<CellEditEvent<Product, String>>() {
            public void handle(CellEditEvent<Product, String> t) {
            	Integer i = t.getTableView().getItems().get(t.getTablePosition().getRow()).getID();
            	Product p = ((Product) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        );
            	p.setProduct_name(t.getNewValue());
            	System.out.println("Arvoja muutettu Productin arvot nyt : " + p.getID() + p.getProduct_name() + p.getProduct_volume() + p.getProduct_weight() + p.getProduct_price());
            }
         });

	    weightCol.setCellFactory(cellFactory3);
	    weightCol.setOnEditCommit(new EventHandler<CellEditEvent<Product, Double>>() {
            public void handle(CellEditEvent<Product, Double> t) {
            	System.out.println("LOL" + t.getTableColumn().getText());
            	Integer i = t.getTableView().getItems().get(t.getTablePosition().getRow()).getID();
            	Product p = ((Product) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        );
            	p.setProduct_weight(t.getNewValue().doubleValue());
                System.out.println("Arvoja muutettu Productin arvot nyt : " + p.getID() + p.getProduct_name() + p.getProduct_volume() + p.getProduct_weight() + p.getProduct_price());
            }
         });

	    volumeCol.setCellFactory(cellFactory3);
	    volumeCol.setOnEditCommit(new EventHandler<CellEditEvent<Product, Double>>() {
            public void handle(CellEditEvent<Product, Double> t) {
            	Integer i = t.getTableView().getItems().get(t.getTablePosition().getRow()).getID();
            	Product p = ((Product) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        );
            	p.setProduct_volume(t.getNewValue().doubleValue());
            	System.out.println("Arvoja muutettu Productin arvot nyt : " + p.getID() + p.getProduct_name() + p.getProduct_volume() + p.getProduct_weight() + p.getProduct_price());
            }
         });

	    priceCol.setCellFactory(cellFactory4);
	    priceCol.setOnEditCommit(new EventHandler<CellEditEvent<Product, Float>>() {
            public void handle(CellEditEvent<Product, Float> t) {
            	Integer i = t.getTableView().getItems().get(t.getTablePosition().getRow()).getID();
            	Product p = ((Product) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        );
            	p.setProduct_price(t.getNewValue().floatValue());
            	System.out.println("Arvoja muutettu Productin arvot nyt : " + p.getID() + p.getProduct_name() + p.getProduct_volume() + p.getProduct_weight() + p.getProduct_price());
            }
         });
	}

	public void SearchManually() throws IOException {
		hae = true;
		if (productName.getText().isEmpty()) {
			hae = false;
		}

		if (hae) {
			HaeTuote();
			if (p == null) //Saatu tuote lista on null eli tyhjä

				return; // error viesti tÃ¤nne ku ei lÃ¶ytynyt mitÃ¤Ã¤n

			try {
				int length = tuoteTable.getItems().size(); //Hae taulun rivien määrä
				if(length>0){//Jos on rivejä
					for(;0<length;){//Poistetaan yksi kerrallaan
						System.out.println("Deleting");
						tuoteTable.getItems().remove(0);
						length--;
					}
				}
				tuoteTable.refresh(); //Varmuuden vuoksi päivitetään TableView
				for (Product pro : p) {
					idCol.setCellValueFactory(new PropertyValueFactory<Product,Integer>("ID"));
					nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("product_name"));
					weightCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("product_weight"));
					priceCol.setCellValueFactory(new PropertyValueFactory<Product, Float>("product_price"));
					volumeCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("product_volume"));
					tuoteTable.getItems().add(pro);
				}
			} catch (Exception e) {
				System.out.println("virhe tuotteiden lisäyksessä");
				e.printStackTrace();
			}

		} else {
			System.out.println("KenttÃ¤ on tÃ¤ytetty vÃ¤Ã¤rÃ¤llÃ¤ tavalla");
		}
	}

	@Override
	public void setMainController(MainController m) {
		mc = m;
	}

	public void HaeTuote() {
		p = mc.haeTuote(productName.getText());
	}

	public void paivitaTuotteet(){

	}

}