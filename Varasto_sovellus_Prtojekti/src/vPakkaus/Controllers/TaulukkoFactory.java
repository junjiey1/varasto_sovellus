package vPakkaus.Controllers;

import java.util.ArrayList;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import vPakkaus.Asiakas;
import vPakkaus.DAO_Objekti;
import vPakkaus.Hyllypaikka;
import vPakkaus.Product;
import vPakkaus.Tuotejoukko;
import javafx.scene.control.cell.PropertyValueFactory;

public class TaulukkoFactory implements TaulukkoFactory_IF{

	@Override
	public Taulukko_IF annaTaulukko(DAO_Objekti obj, ArrayList<DAO_Objekti> lista) {
		if(obj instanceof Product)
			return rakennaProductTaulukko(lista);
		else if(obj instanceof Asiakas)
			return rakennaAsiakasTaulukko(lista);
		else if(obj instanceof Hyllypaikka)
		  return rakennaHyllypaikkaTaulukko(lista);
		else if(obj instanceof Tuotejoukko)
		  return rakennaTuotejoukkoTaulukko(lista);
		return null;
	}

	private Taulukko_IF rakennaTuotejoukkoTaulukko(ArrayList<DAO_Objekti> lista){
	  System.out.println("oooooooooooooo");
	  TableView<DAO_Objekti> joukko_taulukko = new TableView<DAO_Objekti>();
	  TableColumn<DAO_Objekti, String> nimi = new TableColumn<DAO_Objekti, String>();//Nimi
	  TableColumn<DAO_Objekti, String> sijainti = new TableColumn<DAO_Objekti, String>();//Sijainti
	  TableColumn<DAO_Objekti, Integer> maara = new TableColumn<DAO_Objekti, Integer>();//Määrä
	  nimi.setText("Nimi");
	  sijainti.setText("Sijainti");
	  maara.setText("Määrä");
	  joukko_taulukko.getColumns().add(nimi);
	  joukko_taulukko.getColumns().add(sijainti);
	  joukko_taulukko.getColumns().add(maara);
	  for(DAO_Objekti dao : lista){
      nimi.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, String>("tuotteenNimi"));
      sijainti.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, String>("hyllynNimi"));
      maara.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Integer>("maara"));
    }
    return null;

	}


	private Taulukko_IF rakennaAsiakasTaulukko(ArrayList<DAO_Objekti> lista) {
		TableView<DAO_Objekti> asiakas_taulukko = new TableView<DAO_Objekti>();
		TableColumn<DAO_Objekti, String> t1 = new TableColumn<DAO_Objekti, String>();//Nimi
		t1.setText("Nimi");
//		Callback<TableColumn<DAO_Objekti, String>, TableCell<DAO_Objekti, String>> cellFactory2 = new Callback<TableColumn<DAO_Objekti, String>, TableCell<DAO_Objekti, String>>() {
//			public TableCell call(TableColumn p) {
//				return new ProductCellEditor(2,null);
//			}
//		};
//		t1.setCellFactory(cellFactory2);
		asiakas_taulukko.getColumns().add(t1);
		for(int i = 0; i<lista.size();i++){
			t1.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, String>("nimi"));
		}
		asiakas_taulukko.getItems().addAll(lista);
		return new AsiakasTaulukko(asiakas_taulukko);
	}

	private Taulukko_IF rakennaHyllypaikkaTaulukko(ArrayList<DAO_Objekti> lista){
	  TableView<DAO_Objekti> hylly_taulukko = new TableView<DAO_Objekti>();
	  TableColumn<DAO_Objekti, String> t1 = new TableColumn<DAO_Objekti, String>();//Nimi
	  TableColumn<DAO_Objekti, Double> t2 = new TableColumn<DAO_Objekti, Double>();//Leveys
    TableColumn<DAO_Objekti, Double> t3 = new TableColumn<DAO_Objekti, Double>();//Pituus
    TableColumn<DAO_Objekti, Double> t4 = new TableColumn<DAO_Objekti, Double>();//Korkeus
    TableColumn<DAO_Objekti, Double> t5 = new TableColumn<DAO_Objekti, Double>();//MaxPaino
    t1.setText("Nimi");
    t2.setText("Leveys");
    t3.setText("Pituus");
    t4.setText("Korkeus");
    t5.setText("MaxPaino");
    Hyllypaikka[] PaivitettavatHyllyt = new Hyllypaikka[lista.size()];
    System.out.println("koko " + lista.size());
    for(int i = 0; i<lista.size(); i++)
      if((Hyllypaikka)lista.get(i)!=null)
        PaivitettavatHyllyt[i] = (Hyllypaikka)lista.get(i);

    Callback<TableColumn<DAO_Objekti, String>, TableCell<DAO_Objekti, String>> cellFactory2 = new Callback<TableColumn<DAO_Objekti, String>, TableCell<DAO_Objekti, String>>() {
      public TableCell call(TableColumn p) {
        return new HyllyCellEditor(2,PaivitettavatHyllyt);
      }
    };

    Callback<TableColumn<DAO_Objekti, Double>, TableCell<DAO_Objekti, Double>> cellFactory3 = new Callback<TableColumn<DAO_Objekti, Double>, TableCell<DAO_Objekti, Double>>() {
      public TableCell call(TableColumn p) {
        EditingCell e = new HyllyCellEditor(3,PaivitettavatHyllyt);
        return e;
      }
    };

    t1.setCellFactory(cellFactory2);
    t2.setCellFactory(cellFactory3);
    t3.setCellFactory(cellFactory3);
    t4.setCellFactory(cellFactory3);
    t5.setCellFactory(cellFactory3);
    hylly_taulukko.getColumns().add(t1);
    hylly_taulukko.getColumns().add(t2);
    hylly_taulukko.getColumns().add(t3);
    hylly_taulukko.getColumns().add(t4);
    hylly_taulukko.getColumns().add(t5);

    for(int i = 0; i<PaivitettavatHyllyt.length;i++){
      t1.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, String>("nimi"));
      t2.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Double>("leveys"));
      t3.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Double>("pituus"));
      t4.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Double>("korkeus"));
      t5.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Double>("max_paino"));
    }
    return new HyllyTaulukko(hylly_taulukko, PaivitettavatHyllyt);
	}

	private Taulukko_IF rakennaProductTaulukko(ArrayList<DAO_Objekti> lista){
		TableView<DAO_Objekti> product_taulukko = new TableView<DAO_Objekti>();
//		product_taulukko.setId("lolleriino");
//		product_taulukko.setLayoutX(307.0);
//		product_taulukko.setLayoutY(23.0);
//		product_taulukko.setPrefSize(385.0, 430.0);
		TableColumn<DAO_Objekti, Double> t1 = new TableColumn<DAO_Objekti, Double>();//Paino
		TableColumn<DAO_Objekti, String> t2 = new TableColumn<DAO_Objekti, String>();//Nimi
		TableColumn<DAO_Objekti, Double> t3 = new TableColumn<DAO_Objekti, Double>();//Leveys
		TableColumn<DAO_Objekti, Double> t4 = new TableColumn<DAO_Objekti, Double>();//Pituus
		TableColumn<DAO_Objekti, Double> t5 = new TableColumn<DAO_Objekti, Double>();//Korkeus
		TableColumn<DAO_Objekti, Float> t6 = new TableColumn<DAO_Objekti, Float>();//Hinta
		TableColumn<DAO_Objekti, Integer> t7 = new TableColumn<DAO_Objekti, Integer>();//MaxLämpotila
		TableColumn<DAO_Objekti, Integer> t8 = new TableColumn<DAO_Objekti, Integer>();//MinLämpotila
		TableColumn<DAO_Objekti, Object> t9 = new TableColumn<DAO_Objekti, Object>();

		t1.setText("Paino");
		t2.setText("Nimi");
		t3.setText("Leveys");
		t4.setText("Pituus");
		t5.setText("Korkeus");
		t6.setText("Hinta");
		t7.setText("MaxLampo");
		t8.setText("MinLampo");
		t9.setText("Nollaus");

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
		Callback<TableColumn<DAO_Objekti, Object>, TableCell<DAO_Objekti, Object>> cellFactory5 = new Callback<TableColumn<DAO_Objekti, Object>, TableCell<DAO_Objekti, Object>>() {
			public TableCell call(TableColumn p) {
				return new ProductTaulukkoNappi("Nollaa lämpötila", PaivitettavatTuotteet);
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
		product_taulukko.getColumns().add(t9);

		t1.setCellFactory(cellFactory3);
		t2.setCellFactory(cellFactory2);
		t3.setCellFactory(cellFactory3);
		t4.setCellFactory(cellFactory3);
		t5.setCellFactory(cellFactory3);
		t6.setCellFactory(cellFactory4);
		t7.setCellFactory(cellFactory);
		t8.setCellFactory(cellFactory);
		t9.setCellFactory(cellFactory5);

		for(int i = 0; i<PaivitettavatTuotteet.length;i++){
			t1.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Double>("product_weight"));
			t2.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, String>("product_name"));
			t3.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Double>("product_width"));
			t4.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Double>("product_length"));
			t5.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Double>("product_height"));
			t6.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Float>("product_price"));
			t7.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Integer>("max_temperature"));
			t8.setCellValueFactory(new PropertyValueFactory<DAO_Objekti, Integer>("min_temperature"));
			t9.setCellValueFactory(
	                new Callback<TableColumn.CellDataFeatures<DAO_Objekti, Object>,
	                ObservableValue<Object>>() {

            				@Override
            				public ObservableValue<Object> call(CellDataFeatures<DAO_Objekti, Object> arg0) {
            					return new ReadOnlyObjectWrapper<Object>(arg0.getValue());
            				}
        });
		}
		return new ProductTaulukko(product_taulukko, PaivitettavatTuotteet);
	}

}
