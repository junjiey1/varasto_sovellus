package vPakkaus.Controllers;

import java.sql.Date;

import vPakkaus.DB_AccessObject;

public class MainController {
	private DB_AccessObject db;
	private int UserID;
	private String username;

	public MainController()
	{
		System.out.println("Constructing Main Controller");
		db = new DB_AccessObject();
		username = "undefined";
		UserID = -1;
	}

	public boolean LogIn(String username, String password)
	{
		boolean res = false;
		int[] tulos = db.LogIn(username, password);
		if(tulos[0] == 1)
		{
			res = true;
			UserID = tulos[1];
			this.username = username;
		}
		return res;
	}

	public boolean AddProduct(String nimi, double paino, double tilavuus,
			String hyllypaikka, Date saapumispaiva, Date lahtopaiva, float hinta,
			int lisaaja_id, int maara)
	{
		boolean res = db.Lisaa(nimi, paino, tilavuus, hyllypaikka, saapumispaiva,
		lahtopaiva, hinta, lisaaja_id, maara);
		return res;
	}

	public String getName()
	{
		return username;
	}

	public int getID()
	{
		return UserID;
	}

	public void LogOut()
	{
		System.out.println("logged out. Deleting saved user information...");
		UserID = -1;
		username = "undefined";
	}
}
