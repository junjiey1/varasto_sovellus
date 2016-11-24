package vPakkaus;

public class Tuotejoukko implements DAO_Objekti{
	private Product product;
	private Hyllypaikka hylly;
	private int maara;

	//Näitä muuttujia tarvitaan jotta voidaan helposti asettaa Javafx taulukoihin Product ja Hyllypaikka olioiden nimet
	//Ei tarvita gettereitä ja settereitä näille
	private String tuotteenNimi;
	private String hyllynNimi;

	public Tuotejoukko(Product p, Hyllypaikka h, int maara){
		product = p;
		hylly = h;
		this.maara = maara;
		if(p!=null)
		  tuotteenNimi = p.getProduct_name();
		if(h!=null)
		  hyllynNimi = h.getNimi();
	}



	public String getTuotteenNimi() {
    return tuotteenNimi;
  }

  public String getHyllynNimi() {
    return hyllynNimi;
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
