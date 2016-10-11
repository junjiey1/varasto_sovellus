package vPakkaus.Controllers;

import java.util.ArrayList;
import vPakkaus.DB_AccessObject;
import vPakkaus.Product;

public class MainController {
	private DB_AccessObject db;
	private int UserID;
	private String username;

	public MainController() {
		System.out.println("Constructing Main Controller");
		db = new DB_AccessObject();
		username = "undefined";
		UserID = -1;
	}

	public boolean LogIn(String username, String password) {
		boolean res = false;
		int[] tulos = db.LogIn(username, password);
		if (tulos[0] == 1) {
			res = true;
			UserID = tulos[1];
			this.username = username;
		}
		return res;
	}

	public boolean AddProduct(String nimi, double paino, double tilavuus, String hyllypaikka, float hinta, int maara) {
		boolean res = db.Lisaa(nimi, paino, tilavuus, hyllypaikka, hinta, maara);
		return res;
	}
	public ArrayList<Product> haeTuote(String nimi) {
		ArrayList<Product> res = null;
		res = db.findProducts(nimi);


		for (Product p : res) {
			System.out.println(p.getProduct_name() + p.getID());
		}

		if(res==null)
			return null;

		// product-olio
		return res;
	}


	public boolean paivitaTuotteet(ArrayList<Product> products){

		boolean res = !db.updateProducts(products);
		return res;
	}

	public boolean tallennaMuutokset(ArrayList<Product> lista) {
		boolean res = false;
		return res;
	}

	public boolean DeleteProduct(String nimi) {
		boolean res = false;
		return res;
	}

	public String getName() {
		return username;
	}

	public int getID() {
		return UserID;
	}

	public void LogOut() {
		System.out.println("logged out. Deleting saved user information...");
		UserID = -1;
		username = "undefined";
	}
}
