package vPakkaus.Controllers;

import java.util.ArrayList;

import vPakkaus.Asiakas;
import vPakkaus.Hyllypaikka;
import vPakkaus.Product;
import vPakkaus.Tuotejoukko;

public interface MainController_IF {
	public void asetaAktiiviseksiNaytoksi(Nakyma_IF naytto);
	boolean logIn(String username, String password);
	boolean addProduct(Tuotejoukko joukko);
	ArrayList<Product> haeTuote(String nimi);
	public int getID();
	public String getName();
	public boolean paivitaTuotteet(ArrayList<Product> products);
	public ArrayList<Hyllypaikka> haeHyllypaikka(String nimi);
	public boolean paivitaHylly(Hyllypaikka h);
	public void haeAsiakkaat(String nimi);
	public void tallennaAsiakas(Asiakas asiakas);
	public void logOut();
	public void updateAsiakas(Asiakas a);
	public void haeTuotejoukot(String nimi);
}
