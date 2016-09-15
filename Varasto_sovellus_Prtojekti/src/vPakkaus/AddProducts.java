package vPakkaus;

import java.sql.Date;

public class AddProducts {

	int product_quantity, add_user_id;
	Date saapumispaiva, lahtopaiva;
	Product product;

	public AddProducts(Product product, int product_quantity, int add_user_id, Date saapumispaiva, Date lahtopaiva) {

		this.product = product;
		this.product_quantity = product_quantity;
		this.add_user_id = add_user_id;
		this.saapumispaiva = saapumispaiva;
		this.lahtopaiva = lahtopaiva;
	}

	public int getProduct_quantity() {
		return product_quantity;
	}

	public void setProduct_quantity(int product_quantity) {
		this.product_quantity = product_quantity;
	}

	public int getAdd_user_id() {
		return add_user_id;
	}

	public void setAdd_user_id(int add_user_id) {
		this.add_user_id = add_user_id;
	}

	public Date getSaapumispaiva() {
		return saapumispaiva;
	}

	public void setSaapumispaiva(Date saapumispaiva) {
		this.saapumispaiva = saapumispaiva;
	}

	public Date getLahtopaiva() {
		return lahtopaiva;
	}

	public void setLahtopaiva(Date lahtopaiva) {
		this.lahtopaiva = lahtopaiva;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}


}
