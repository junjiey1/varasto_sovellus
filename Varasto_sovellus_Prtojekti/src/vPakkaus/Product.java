package vPakkaus;

import java.sql.Date;

public class Product {

	private String product_name, product_location;
	private Double product_weight, product_volume;
	private float product_price;
	private int ID, maara;

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

public String getProduct_name() {
	return product_name;
}
public void setProduct_name(String product_name) {
	this.product_name = product_name;
}
public String getProduct_location() {
	return product_location;
}
public void setProduct_location(String product_location) {
	this.product_location = product_location;
}
public Double getProduct_weight() {
	return product_weight;
}
public void setProduct_weight(Double product_weight) {
	this.product_weight = product_weight;
}
public Double getProduct_volume() {
	return product_volume;
}
public void setProduct_volume(Double product_volume) {
	this.product_volume = product_volume;
}
public float getProduct_price() {
	return product_price;
}
public void setProduct_price(float product_price) {
	this.product_price = product_price;
}
public void setID(int id){
	ID = id;
}
public int getID(){
	return ID;
}

}
