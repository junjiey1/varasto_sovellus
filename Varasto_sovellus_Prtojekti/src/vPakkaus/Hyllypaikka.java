package vPakkaus;

public class Hyllypaikka {
	public Hyllypaikka(String nimi){
		this.nimi=nimi;
	}

	public Hyllypaikka(String nimi, double pituus, double leveys, double syvyys, double lampotila){
		this.pituus = pituus;
		this.leveys = leveys;
		this.syvyys = syvyys;
		this.lämpötila = lampotila;
		this.nimi = nimi;
	}

	private String nimi;

	public String getNimi() {
		return nimi;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	private double lämpötila;

	public double getLämpötila() {
		return lämpötila;
	}

	public void setLämpötila(double lämpötila) {
		this.lämpötila = lämpötila;
	}


	private double pituus;
	public double getPituus() {
		return pituus;
	}

	public void setPituus(double pituus) {
		this.pituus = pituus;
	}

	public double getLeveys() {
		return leveys;
	}

	public void setLeveys(double leveys) {
		this.leveys = leveys;
	}

	public double getSyvyys() {
		return syvyys;
	}

	public void setSyvyys(double syvyys) {
		this.syvyys = syvyys;
	}

	private double leveys;
	private double syvyys;
}
