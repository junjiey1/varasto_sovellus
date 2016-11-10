package vPakkaus.Controllers;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import vPakkaus.DAO_Objekti;
import vPakkaus.Product;
import javafx.scene.control.cell.PropertyValueFactory;

public class TaulukkoFactory implements TaulukkoFactory_IF{

	@Override
	public Taulukko_IF annaTaulukko(DAO_Objekti obj, ArrayList<DAO_Objekti> lista) {
		if(obj instanceof Product)
			return rakennaProductTaulukko(lista);
		return null;
	}


	private Taulukko_IF rakennaProductTaulukko(ArrayList<DAO_Objekti> lista){
		TableView<DAO_Objekti> product_taulukko = new TableView<DAO_Objekti>();
		product_taulukko.setId("lolleriino");
		product_taulukko.setLayoutX(307.0);
		product_taulukko.setLayoutY(23.0);
		product_taulukko.setPrefSize(385.0, 430.0);
		System.out.println("p " +product_taulukko.toString());
		TableColumn<DAO_Objekti, Double> t1 = new TableColumn<DAO_Objekti, Double>();//Paino
		TableColumn<DAO_Objekti, String> t2 = new TableColumn<DAO_Objekti, String>();//Nimi
		TableColumn<DAO_Objekti, Double> t3 = new TableColumn<DAO_Objekti, Double>();//Leveys
		TableColumn<DAO_Objekti, Double> t4 = new TableColumn<DAO_Objekti, Double>();//Pituus
		TableColumn<DAO_Objekti, Double> t5 = new TableColumn<DAO_Objekti, Double>();//Korkeus
		TableColumn<DAO_Objekti, Float> t6 = new TableColumn<DAO_Objekti, Float>();//Hinta
		TableColumn<DAO_Objekti, Integer> t7 = new TableColumn<DAO_Objekti, Integer>();//MaxLämpotila
		TableColumn<DAO_Objekti, Integer> t8 = new TableColumn<DAO_Objekti, Integer>();//MinLämpotila
		t1.setText("Paino");
		t2.setText("Nimi");
		t3.setText("Leveys");
		t4.setText("Pituus");
		t5.setText("Korkeus");
		t6.setText("Hinta");
		t7.setText("MaxLampo");
		t8.setText("MinLampo");

		Product[] PaivitettavatTuotteet = new Product[lista.size()];
		System.out.println("koko " + lista.size());
		for(int i = 0; i<lista.size(); i++)
			if((Product)lista.get(i)!=null)
				PaivitettavatTuotteet[i] = (Product)lista.get(i);

		Callback<TableColumn<DAO_Objekti, Integer>, TableCell<DAO_Objekti, Integer>> cellFactory = new Callback<TableColumn<DAO_Objekti, Integer>, TableCell<DAO_Objekti, Integer>>() {
			public TableCell call(TableColumn p) {
				return new ProductCellEditor(1,PaivitettavatTuotteet);
			}
		};
		Callback<TableColumn<DAO_Objekti, String>, TableCell<DAO_Objekti, String>> cellFactory2 = new Callback<TableColumn<DAO_Objekti, String>, TableCell<DAO_Objekti, String>>() {
			public TableCell call(TableColumn p) {
				return new ProductCellEditor(2,PaivitettavatTuotteet);
			}
		};
		Callback<TableColumn<DAO_Objekti, Double>, TableCell<DAO_Objekti, Double>> cellFactory3 = new Callback<TableColumn<DAO_Objekti, Double>, TableCell<DAO_Objekti, Double>>() {
			public TableCell call(TableColumn p) {
				EditingCell e = new ProductCellEditor(3,PaivitettavatTuotteet);
				return e;
			}
		};

		Callback<TableColumn<DAO_Objekti, Float>, TableCell<DAO_Objekti, Float>> cellFactory4 = new Callback<TableColumn<DAO_Objekti, Float>, TableCell<DAO_Objekti, Float>>() {
			public TableCell call(TableColumn p) {
				return new ProductCellEditor(4,PaivitettavatTuotteet);
			}
		};
		product_taulukko.getColumns().add(t1);
		product_taulukko.getColumns().add(t2);
		product_taulukko.getColumns().add(t3);
		product_taulukko.getColumns().add(t4);
		product_taulukko.getColumns().add(t5);
		product_taulukko.getColumns().add(t6);
		product_taulukko.getColumns().add(t7);
		product_taulukko.getColumns().add(t8);

		t1.setCellFactory(cellFactory3);
		t2.setCellFactory(cellFactory2);
		t3.setCellFactory(cellFactory3);
		t4.setCellFactory(cellFactory3);
		t5.setCellFactory(cellFactory3);
		t6.setCellFactory(cellFactory4);
		t7.setCellFactory(cellFactory);
		t8.setCellFactory(cellFactory);

		for(int i = 0; i<PaivitettavatTuotteet.length;i++){
			t1.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Double>("product_weight"));
			t2.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, String>("product_name"));
			t3.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Double>("product_width"));
			t4.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Double>("product_length"));
			t5.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Double>("product_height"));
			t6.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Float>("product_price"));
			t7.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Integer>("max_temperature"));
			t8.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Integer>("min_temperature"));
		}
		return new ProductTaulukko(product_taulukko, PaivitettavatTuotteet);
	}

}
