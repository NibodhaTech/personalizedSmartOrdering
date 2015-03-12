package com.nibodha.ordering.model;

import java.util.List;

public class ProductContextWrapper {
	private List<ProductContext> products;

	/**
	 * @return the products
	 */
	public List<ProductContext> getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProducts(List<ProductContext> products) {
		this.products = products;
	}
}
