package vPakkaus;

public class Hyllypaikka {
	public Hyllypaikka(double pituus, double leveys, double syvyys, int maara, int tuotteenID){
		this.pituus = pituus;
		this.leveys = leveys;
		this.syvyys = syvyys;
		this.maara = maara;
		this.tuoteID = tuotteenID;
	}

	private int tuoteID;

	public int getTuoteID() {
		return tuoteID;
	}

	public void setTuoteID(int tuoteID) {
		this.tuoteID = tuoteID;
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

	public int getMaara() {
		return maara;
	}

	public void setMaara(int maara) {
		this.maara = maara;
	}

	private double leveys;
	private double syvyys;
	private int maara;
}
