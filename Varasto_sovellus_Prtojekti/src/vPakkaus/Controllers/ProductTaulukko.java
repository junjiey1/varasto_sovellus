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
	private Product[] paivitettavatTuotteet;
	private ArrayList<Product> p = new ArrayList<Product>();
	private String error;

	ProductTaulukko(TableView<DAO_Objekti> tuoteTaulukko, Product[] lista){
		taulukko = tuoteTaulukko;
		paivitettavatTuotteet = lista;
		int lask = 0;
		for(Product o : lista){
			if(o!=null){
				p.add(o);
				taulukko.getItems().add(o);
				lista[lask] = null;
				lask++;
			}
		}
	}

	private ArrayList<Product> convertToArrayList() {
		ArrayList<Product> res = new ArrayList<Product>();
		int lask=0;
		for (Product p : paivitettavatTuotteet) {
			if (p != null){
				if(!isValid(p)){
					return null;
				}else
					res.add(p);
				lask++;
			}
		}
		return res;
	}

	private boolean isValid(Product p){
		if(p.getMax_temperature()!=null && p.getMin_temperature()==null
			|| p.getMin_temperature()!=null && p.getMax_temperature()==null){
			error = "Virhe! tuotteella " + p.getProduct_name() + " pitää olla määriteltynä sekä "
					+ "minimi, että maksimi lämpotilat";
			return false;
		}else if(p.getTemp() && p.getMin_temperature()>p.getMax_temperature()){
			error = "Virhe! tuotteella " + p.getProduct_name() + " on määritelty minimi lämpötila "
					+ "suuremmaksi kuin maksimi lämpötila";
			return false;
		}
		return true;
	}

	private boolean isEmpty() {
		for (Product p : paivitettavatTuotteet) {
			if (p != null)
				return false;
		}
		return true;
	}

	@Override
	public boolean paivitaTietokantaan(MainController_IF mc, Nakyma_IF nakyma) {
		if (paivitettavatTuotteet == null || isEmpty()) // Tuote lista on tyhjä käyttäjä ei oo muokannut tuotteita
			return false;
		System.out.println("Tyhjä? " + isEmpty());
		ArrayList<Product> res = convertToArrayList();
		if(res==null){
			nakyma.virheIlmoitus(error);
			return false;
		}
		if (mc.paivitaTuotteet(res)) {
			for(int i = 0 ; i<paivitettavatTuotteet.length; i++)
				if(paivitettavatTuotteet[i]!=null)
					paivitettavatTuotteet[i]=null;
			//PaivitettavatTuotteet = new Product[p.size()];//Pitäisi nyt käydä aina manuaalisesti tyhjentämässä
			return true;
		} else
			return false;
	}

	@Override
	public void addTableView(TableView table) {
		taulukko = table;
	}

	public TableView<DAO_Objekti> getTaulukko() {
		return taulukko;
	}

	@Override
	public DAO_Objekti getObject(int index) {
		return paivitettavatTuotteet[index];
	}

}
