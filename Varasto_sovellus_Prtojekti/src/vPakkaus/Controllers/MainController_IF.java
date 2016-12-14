package vPakkaus.Controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import vPakkaus.Asiakas;
import vPakkaus.Hyllypaikka;
import vPakkaus.Product;
import vPakkaus.Tuotejoukko;
import vPakkaus.Varastoliikenne;

public interface MainController_IF {
	public void asetaAktiiviseksiNaytoksi(Nakyma_IF naytto);
	public boolean logIn(String username, String password);
	public boolean addProduct(Tuotejoukko joukko);
	public void paivitaTuoteRivi(Tuotejoukko tjk);
	public void haeTuotejoukkoHyllysta(String hyllynTunnut, String tuotteenNimi);
	public ArrayList<Product> haeTuote(String nimi);
	public int getID();
	public String getName();
	public boolean paivitaTuotteet(ArrayList<Product> products);
	public ArrayList<Hyllypaikka> haeHyllypaikka(String nimi);
	public boolean paivitaHylly(Hyllypaikka h);
	public Asiakas haeAsiakas(Object criteria);
	public void haeAsiakkaat(String nimi);
	public void tallennaAsiakas(Asiakas asiakas);
	public void logOut();
	public void updateAsiakas(Asiakas a);
	public void haeTuotejoukot(String nimi);
	public boolean luoUusiLahetys(LocalDate pvm, String osoite, int asiakasID, ArrayList<Tuotejoukko> tjk);
	public boolean paivitaLahetys(Varastoliikenne vl);
	public List<Varastoliikenne> haeLahetykset(int id);
	public void deleteLahetys(int id);
	public TreeMap<Date, Integer> haeTietoja(Date d1, Date d2, String tuotteenNimi);
}
