package vPakkaus.Controllers;

import java.io.IOException;

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
			try{
			idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
			nameCol.setCellValueFactory(new PropertyValueFactory<>("nimi"));
			weightCol.setCellValueFactory(new PropertyValueFactory<>("paino"));
			priceCol.setCellValueFactory(new PropertyValueFactory<>("hinta"));
			volumeCol.setCellValueFactory(new PropertyValueFactory<>("tilavuus"));

			}catch(Exception e){

			}

		}else {

			System.out.println("Kenttä on täytetty väärällä tavalla");
		}
	}



	@Override
	public void setMainController(MainController m) {
		mc = m;
	}

	public void HaeTuote(){
		String nimi=null;
		//Hae tekstikentän arvo
		mc.haeTuote(nimi);
	}

}
