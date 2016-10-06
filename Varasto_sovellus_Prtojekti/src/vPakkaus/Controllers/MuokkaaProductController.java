package vPakkaus.Controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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

	public void initialize(){
		System.out.println("menee");
		idCol = new TableColumn<Product, Integer>("ID");
		tuoteTable.setEditable(true);
		idCol.setEditable(true);
		nameCol.setEditable(true);
	}

	public void SearchManually()throws IOException {
		hae= true;
		if(productName.getText().isEmpty()){
			hae=false;
		}

		if(hae){
			HaeTuote();
			if(p==null)
				return; //error viesti tänne ku ei löytynyt mitään
			try{
				for(Product pro : p){
					idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
					//idCol.setCellFactory(TextFieldTableCell.<Product>forTableColumn());
//					idCol.setOnEditCommit(
//			            (CellEditEvent<Product, Integer> t) -> {
//			                ((Product) t.getTableView().getItems().get(
//			                        t.getTablePosition().getRow())
//			                        ).setID(t.getNewValue());
//			        });
					nameCol.setCellValueFactory(new PropertyValueFactory<Product,String>("product_name"));
					weightCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("product_weight"));
					priceCol.setCellValueFactory(new PropertyValueFactory<Product,Float>("product_price"));
					volumeCol.setCellValueFactory(new PropertyValueFactory<Product,Double>("product_volume"));
					tuoteTable.getItems().add(pro);
				}
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
		p = mc.haeTuote(productName.getText());
	}

}
