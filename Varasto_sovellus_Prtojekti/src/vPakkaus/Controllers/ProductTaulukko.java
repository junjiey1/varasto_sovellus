package vPakkaus.Controllers;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import vPakkaus.DAO_Objekti;
import vPakkaus.Product;

public class ProductTaulukko implements Taulukko_IF{

	private TableView<DAO_Objekti> taulukko;
	private Product[] PaivitettavatTuotteet;
	private ArrayList<Product> p = new ArrayList<Product>();

	ProductTaulukko(TableView<DAO_Objekti> tuoteTaulukko, Product[] lista){
		taulukko = tuoteTaulukko;
		PaivitettavatTuotteet = lista;
		int lask = 0;
		for(Product o : lista){
			System.out.println(o.getProduct_name());
			if(o!=null){
				p.add(o);
				taulukko.getItems().add(o);
				lista[lask] = null;
				lask++;
			}
		}
		//taulukko.getItems().add(lista[0]);
		//taulukko.getItems().add(lista[1]);
	}

	private ArrayList<Product> convertToArrayList() {
		ArrayList<Product> res = new ArrayList<Product>();
		for (Product p : PaivitettavatTuotteet) {
			if (p != null)
				res.add(p);
		}
		return res;
	}

	private boolean isEmpty() {
		for (Product p : PaivitettavatTuotteet) {
			if (p != null)
				return false;
		}
		return true;
	}

	@Override
	public void prepareEditArray() {

	}

	@Override
	public boolean paivitaTietokantaan(MainController_IF mc) {
		if (PaivitettavatTuotteet == null || isEmpty()) // Tuote lista on tyhjä käyttäjä ei oo muokannut tuotteita
			return false;
		System.out.println("Tyhjä? " + isEmpty());
		if (mc.paivitaTuotteet(convertToArrayList())) {
			for(int i = 0 ; i<PaivitettavatTuotteet.length; i++)
				if(PaivitettavatTuotteet[i]!=null)
					PaivitettavatTuotteet[i]=null;
			//PaivitettavatTuotteet = new Product[p.size()];//Pitäisi nyt käydä aina manuaalisesti tyhjentämässä
			return true;
		} else
			return false;
	}

	@Override
	public void addTableView(TableView table) {
		taulukko = table;
	}

	@Override
	public void addInstance(DAO_Objekti obj) {
		if(!(obj instanceof Product))
			return;
		p.add((Product)obj);
	}

	public TableView<DAO_Objekti> getTaulukko() {
		return taulukko;
	}

	@Override
	public DAO_Objekti getObject(int index) {
		return PaivitettavatTuotteet[index];
	}

}
