package vPakkaus;

public class Asiakas implements DAO_Objekti{

	private String nimi, osoit, kaupun, emai, numero;
	private int posnumero, ID;


	public Asiakas(String nimi, String osoite, String kaupunki, String email, String puhelinnumero, int postinumero){
		this.nimi = nimi;
		osoit = osoite;
		kaupun = kaupunki;
		emai = email;
		numero = puhelinnumero;
		posnumero = postinumero;
		ID=-1;
	}

	public String getNimi() {
		return nimi;
	}



	public void setNimi(String nimi) {
		this.nimi = nimi;
	}



	public String getOsoit() {
		return osoit;
	}



	public void setOsoit(String osoit) {
		this.osoit = osoit;
	}



	public String getKaupun() {
		return kaupun;
	}



	public void setKaupun(String kaupun) {
		this.kaupun = kaupun;
	}



	public String getEmai() {
		return emai;
	}



	public void setEmai(String emai) {
		this.emai = emai;
	}



	public String getNumero() {
		return numero;
	}



	public void setNumero(String numero) {
		this.numero = numero;
	}



	public int getPosnumero() {
		return posnumero;
	}



	public void setPosnumero(int posnumero) {
		this.posnumero = posnumero;
	}



	public int getID() {
		return ID;
	}



	public void setID(int iD) {
		ID = iD;
	}



	@Override
	public boolean paivitaMuuttuja(String muuttujanNimi) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object haeMuuttuja(String muuttujanNimi) {
		// TODO Auto-generated method stub
		return null;
	}

}