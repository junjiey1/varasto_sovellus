package vPakkaus.Controllers;

import java.util.ArrayList;

import vPakkaus.Product;
import vPakkaus.Tuotejoukko;

public interface MainController_IF {
	public void liitaNaytto(Nakyma_IF naytto);
	boolean LogIn(String username, String password);
	boolean AddProduct(Tuotejoukko joukko);
	ArrayList<Product> haeTuote(String nimi);
	public int getID();
	public String getName();
	public boolean paivitaTuotteet(ArrayList<Product> products);
	public void LogOut();
}
