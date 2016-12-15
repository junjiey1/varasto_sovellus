package vPakkaus;

public class Hyllypaikka implements DAO_Objekti{

	public Hyllypaikka(String nimi){
		this.nimi=nimi;
	}

	public Hyllypaikka(String nimi, double pituus, double leveys, double korkeus, int lampotila, double max_paino){
		this.pituus = pituus;
		this.leveys = leveys;
		this.korkeus = korkeus;
		this.lämpötila = lampotila;
		this.nimi = nimi;
		this.max_paino = max_paino;
	}

	private String nimi;
	private double leveys;
	private double korkeus;
	private double max_paino;
	private int lämpötila;

	public String getNimi() {
		return nimi;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	public int getLämpötila() {
		return lämpötila;
	}

	public void setLämpötila(int lämpötila) {
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

	public double getKorkeus() {
		return korkeus;
	}

	public void setKorkeus(double korkeus) {
		this.korkeus = korkeus;
	}

	public double getMax_paino() {
		return max_paino;
	}

	public void setMax_paino(double max_paino) {
		this.max_paino = max_paino;
	}

  @Override
  public String toString() {
    return "Hyllypaikka [nimi=" + nimi + ", leveys=" + leveys + ", korkeus=" + korkeus
        + ", max_paino=" + max_paino + ", lämpötila=" + lämpötila + ", pituus=" + pituus + "]";
  }
}
