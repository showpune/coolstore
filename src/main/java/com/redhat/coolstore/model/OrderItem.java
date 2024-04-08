package com.redhat.coolstore.model;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "ORDER_ITEMS")
public class OrderItem implements Serializable {
	private static final long serialVersionUID = 64565445665456666L;

	@Id
	@Column(name="ID")
	@GeneratedValue
	private long id;

	private int quantity;

	private String productId;

	public OrderItem() {}

	public String getProductId() {
		return productId;
	}

	@Column(name = "PRODUCT_ID") // Update the column name as well
	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Column(name = "QUANTITY") // Update the column name as well
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderItem [productId=" + productId + ", quantity=" + quantity + "]";
	}

}
