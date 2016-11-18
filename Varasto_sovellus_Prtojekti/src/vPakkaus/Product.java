package vPakkaus;

import java.util.ArrayList;

/**
 *
 * Luokka vastaa tavaran konstruktori seka getteri ja setteri tavaran tiedolle.
 *
 */
public class Product implements DAO_Objekti{

	private String product_name;
	private Integer min_temperature, max_temperature;
	private Double product_weight, product_volume, product_length, product_height, product_width;
	private float product_price;
	private int ID;
	private boolean temp;

	/**
	 * Tavaran konstruktori,missä on kaikki tavaran liittyvät tiedot.
	 *
	 * @param product_name
	 *            Tavaran nimi
	 * @param product_location
	 *            Tavaran sijainnin
	 * @param product_weight
	 *            Tavaran paino
	 * @param product_volume
	 *            Tavaran tilavuus
	 * @param product_price
	 *            Tavaran hinta
	 * @param maara
	 *            Tavaran maara
	 */

	public Product(String product_name, Double product_weight, Double product_width,
	Double product_height, Double product_length,float product_price){
		this.product_name = product_name;
		this.product_weight = product_weight;
		this.product_price = product_price;
		this.product_height = product_height;
		this.product_width = product_width;
		this.product_length = product_length;
		ID = 0;
		min_temperature = null;
		max_temperature = null;
		temp=false;
		this.product_volume = product_height * product_length * product_width;
	}


	public boolean getTemp() {
		return temp;
	}
	public void setTemp(boolean temp) {
		this.temp = temp;
	}
	public Double getProduct_length() {
		return product_length;
	}

	public void setProduct_length(Double product_length) {
		this.product_length = product_length;
	}

	public Double getProduct_height() {
		return product_height;
	}

	public void setProduct_height(Double product_height) {
		this.product_height = product_height;
	}

	public Double getProduct_width() {
		return product_width;
	}

	public void setProduct_width(Double product_width) {
		this.product_width = product_width;
	}

	/**
	 * Getteri tavaroiden nimelle
	 *
	 * @return Tavaran nimi
	 */
	public String getProduct_name() {
		return product_name;
	}

	/**
	 * Setteri tavaroiden nimelle
	 *
	 * @param product_name
	 *            Tavaran nimi
	 */
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}



	/**
	 * Getteri tavaran painolle
	 *
	 * @return Tavaran paino
	 */
	public Double getProduct_weight() {
		return product_weight;
	}

	/**
	 * Setteri tavaran painolle
	 *
	 * @param product_weight
	 *            Tavaran paino
	 */

	public void setProduct_weight(Double product_weight) {
		this.product_weight = product_weight;
	}

	/**
	 * Getteri tavaran tilavuudelle
	 *
	 * @return Tavaran tilavuus
	 */
	public Double getProduct_volume() {
		return product_volume;
	}

	/**
	 * Setteri tavaran tilavuudelle.
	 *
	 * @param product_volume
	 *            Tavaran tilavuus
	 */
	public void setProduct_volume(Double product_volume) {
		this.product_volume = product_volume;
	}

	/**
	 * Getteri tavaran hinnalle.
	 *
	 * @return Tavaran hinta.
	 */
	public float getProduct_price() {
		return product_price;
	}

	/**
	 * Setteri tavaran hinnalle.
	 *
	 * @param product_price
	 *            Tavaran hinta.
	 */
	public void setProduct_price(float product_price) {
		this.product_price = product_price;
	}

	/**
	 * Setteri tavaran ID:lle
	 *
	 * @param id
	 *            Tavaran ID
	 */
	public void setID(int id) {
		ID = id;
	}

	/**
	 * Getteri tavaran ID:lle
	 *
	 * @return Tavaran ID
	 */

	public int getID() {
		return ID;
	}

	/**
	 * Getteri tavaran maaralle.
	 *
	 * @return Tavaran maara
	 */

	/**
	 * Setteri tavaran maaralle.
	 *
	 * @param maara
	 *            Tavaran maara
	 */

	/**
	 * Palauttaa kaikki tiedot String muodoksi.
	 */
	@Override
	public String toString() {
		return "Product [product_name=" + product_name + ", product_weight="
				+ product_weight + ", product_volume=" + product_volume + ", product_price=" + product_price + ", ID="
				+ ID + ", TempSet=" + temp + ", MinTemp=" + min_temperature + ", max_temperature=" +  max_temperature +", product_length="+product_length+"]";
	}

	public Integer getMin_temperature() {
		return min_temperature;
	}

	public void setMin_temperature(Integer min_temperature) {
		this.min_temperature = min_temperature;
	}

	public Integer getMax_temperature() {
		return max_temperature;
	}

	public void setMax_temperature(Integer max_temperature) {
		this.max_temperature = max_temperature;
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
