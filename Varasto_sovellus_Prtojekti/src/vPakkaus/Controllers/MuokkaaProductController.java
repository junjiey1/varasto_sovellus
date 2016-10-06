package vPakkaus.Controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import vPakkaus.Product;

public class MuokkaaProductController implements SetMainController{

	@FXML
	private TextField productName;

	@FXML
	private TableView<Product> tuoteTable;

	@FXML
	private TableColumn<Product, Integer> idCol;

	@FXML
	private TableColumn<Product,String> nameCol;

	@FXML
	private TableColumn<Product, Double > weightCol;

	@FXML
	private TableColumn<Product,Float> priceCol;

	@FXML
	private TableColumn<Product,Double> volumeCol;


	private MainController mc;
	private ArrayList<Product> p;
	ObservableList<String> productTextFiles = FXCollections.observableArrayList();
	ObservableList<Product> tuote = FXCollections.observableArrayList();
	boolean hae;



	public void SearchManually()throws IOException {
		hae= true;
		if(productName.getText().isEmpty()){
			hae=false;
		}

		if(hae){
			HaeTuote();
			if(p==null)

			try{
				for(Product pro : p){
					idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
					nameCol.setCellValueFactory(new PropertyValueFactory<>("nimi"));
					weightCol.setCellValueFactory(new PropertyValueFactory<>("paino"));
					priceCol.setCellValueFactory(new PropertyValueFactory<>("hinta"));
					volumeCol.setCellValueFactory(new PropertyValueFactory<>("tilavuus"));
				}
			}catch(Exception e){

			}

		}else {

			System.out.println("KenttÃ¤ on tÃ¤ytetty vÃ¤Ã¤rÃ¤llÃ¤ tavalla");
		}
	}



	@Override
	public void setMainController(MainController m) {
		mc = m;
	}

	public void HaeTuote(){
		p = mc.haeTuote(productName.getText());
	}

}
