package vPakkaus.Controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
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
	private Callback<TableColumn, TableCell> cellFactory;


	private MainController mc;
	private ArrayList<Product> p;
	ObservableList<String> productTextFiles = FXCollections.observableArrayList();
	ObservableList<Product> tuote = FXCollections.observableArrayList();
	boolean hae;





//	private class RowSelectChangeListener implements ChangeListener {
//			        @Override
//			        public void changed(ObservableValue ov,Number oldVal, Number newVal) {
//			            int ix = newVal.intValue();
//			            if ((ix = tuote.size())) {
//			                return; // invalid data
//			            }
//			            Product p = tuote.get(ix);
//			            actionStatus.setText(p.toString());
//			        }
//
//					@Override
//					public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//						// TODO Auto-generated method stub
//
//					}
//			    }




	public void initialize(){
		tuoteTable = new TableView<>();
		tuoteTable.setEditable(true);
		System.out.println("menee");

		idCol = new TableColumn<Product, Integer>("id");
		nameCol = new TableColumn<Product, String>("nimi");
		weightCol = new TableColumn<Product, Double>("weight");
		priceCol = new TableColumn<Product, Float>("hinta");
		volumeCol = new TableColumn<Product, Double>("tilavuus");

		idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
		nameCol.setCellValueFactory(new PropertyValueFactory<Product,String>("product_name"));
		weightCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("product_weight"));
		priceCol.setCellValueFactory(new PropertyValueFactory<Product,Float>("product_price"));
		volumeCol.setCellValueFactory(new PropertyValueFactory<Product,Double>("product_volume"));

		//tuoteTable.getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener());
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
					tuote.add(pro);
					System.out.println(pro.getID());
//					idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
//					nameCol.setCellValueFactory(new PropertyValueFactory<Product,String>("product_name"));
//					weightCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("product_weight"));
//					priceCol.setCellValueFactory(new PropertyValueFactory<Product,Float>("product_price"));
//					volumeCol.setCellValueFactory(new PropertyValueFactory<Product,Double>("product_volume"));
//					idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
//					idCol.setEditable(true);
					//idCol.setCellFactory(cellFactory);
					//idCol.setCellFactory(TextFieldTableCell.<Product>forTableColumn());

//					idCol.setOnEditStart(
//			            (CellEditEvent<Product, Integer> t) -> {
//			                ((Product) t.getTableView().getItems().get(
//			                        t.getTablePosition().getRow())
//			                        ).setID(1);
//			        });
//					idCol.setCellValueFactory(cellData -> {
//			            Product rowIndex = cellData.getValue();
//			            return new ReadOnlyIntegerWrapper(pro.getID());
//			        });
//					nameCol.setCellValueFactory(new PropertyValueFactory<Product,String>("product_name"));
//					weightCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("product_weight"));
//					priceCol.setCellValueFactory(new PropertyValueFactory<Product,Float>("product_price"));
//					volumeCol.setCellValueFactory(new PropertyValueFactory<Product,Double>("product_volume"));
					//tuoteTable.getItems().add(pro);

				}

				for(Product p : tuote){
					System.out.println("-"+p.getID());
				}
				tuoteTable.setItems(tuote);
				//tuoteTable.getColumns().addAll(idCol, nameCol, weightCol, priceCol, volumeCol);
			}catch(Exception e){
				System.out.println("lol");
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
