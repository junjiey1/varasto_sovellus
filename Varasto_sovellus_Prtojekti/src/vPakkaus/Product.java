package vPakkaus;

import java.sql.Date;
/**
 *
 * Luokka vastaa tavaran konstruktori seka getteri ja setteri tavaran tiedolle.
 *
 */
public class Product {

	private String product_name, product_location;
	private Double product_weight, product_volume;
	private float product_price;
	private int ID, maara;

	/**
	 * Tavaran konstruktori,missä on kaikki tavaran liittyvät tiedot.
	 *
	 * @param product_name Tavaran nimi
	 * @param product_location Tavaran sijainnin
	 * @param product_weight Tavaran paino
	 * @param product_volume Tavaran tilavuus
	 * @param product_price Tavaran hinta
	 * @param maara Tavaran maara
	 */
public Product(String product_name, String product_location, Double product_weight, Double product_volume,
			float product_price, int maara) {

		this.product_name = product_name;
		this.product_location = product_location;
		this.product_weight = product_weight;
		this.product_volume = product_volume;
		this.product_price = product_price;
		ID=0;
		this.maara = maara;
	}

/**
 * Getteri tavaroiden nimelle
 * @return Tavaran nimi
 */
public String getProduct_name() {
	return product_name;
}

/**
 *  Setteri tavaroiden nimelle
 * @param product_name Tavaran nimi
 */
public void setProduct_name(String product_name) {
	this.product_name = product_name;
}

/**
 * Getteri tavaran sijainnille
 * @return Tavaran sijainti
 */
public String getProduct_location() {
	return product_location;
}

/**
 * setteri tavaran sijainnille
 *
 * @param product_location Tavaran sijainti
 */
public void setProduct_location(String product_location) {
	this.product_location = product_location;
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
 * @param product_weight Tavaran paino
 */

public void setProduct_weight(Double product_weight) {
	this.product_weight = product_weight;
}

/**
 * Getteri tavaran tilavuudelle
 * @return Tavaran tilavuus
 */
public Double getProduct_volume() {
	return product_volume;
}
/**
 * Setteri tavaran tilavuudelle.
 *
 * @param product_volume Tavaran tilavuus
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
 * @param product_price Tavaran hinta.
 */
public void setProduct_price(float product_price) {
	this.product_price = product_price;
}

/**
 * Setteri tavaran ID:lle
 *
 * @param id Tavaran ID
 */
public void setID(int id){
	ID = id;
}
/**
 * Getteri tavaran ID:lle
 * @return Tavaran ID
 */

public int getID(){
	return ID;
}
/**
 * Getteri tavaran maaralle.
 *
 * @return Tavaran maara
 */
public int getMaara() {
	return maara;
}
/**
 * Setteri tavaran maaralle.
 *
 * @param maara Tavaran maara
 */
public void setMaara(int maara) {
	this.maara = maara;
}

/**
 * Palauttaa kaikki tiedot String muodoksi.
 */
@Override
public String toString() {
	return "Product [product_name=" + product_name + ", product_location=" + product_location + ", product_weight="
			+ product_weight + ", product_volume=" + product_volume + ", product_price=" + product_price + ", ID=" + ID
			+ ", maara=" + maara + "]";
}


}
