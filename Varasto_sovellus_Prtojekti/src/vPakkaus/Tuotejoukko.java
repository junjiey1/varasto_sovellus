package vPakkaus;

public class Tuotejoukko {
	private Product product;
	private Hyllypaikka hylly;
	private int maara;

	public Tuotejoukko(Product p, Hyllypaikka h, int maara){
		product = p;
		hylly = h;
		this.maara = maara;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Hyllypaikka getHylly() {
		return hylly;
	}

	public void setHylly(Hyllypaikka hylly) {
		this.hylly = hylly;
	}

	public int getMaara() {
		return maara;
	}

	public void setMaara(int maara) {
		this.maara = maara;
	}


}
