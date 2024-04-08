package com.redhat.coolstore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.Dependent;

@Dependent
public class ShoppingCart implements Serializable {

	private static final long serialVersionUID = -1108043957592113528L;

	private double cartItemTotal;

	private double cartItemPromoSavings;

	private double shippingTotal;

	private double shippingPromoSavings;

	private double cartTotal;

	private List<ShoppingCartItem> shoppingCartItemList = new ArrayList<ShoppingCartItem>();

	public ShoppingCart() {
	}

	// getters and setters for shoppingCartItemList, cartItemTotal, cartItemPromoSavings, shippingTotal, shippingPromoSavings, cartTotal

	// methods for adding and removing ShoppingCartItem

	@Override
	public String toString() {
		return "ShoppingCart [cartItemTotal=" + cartItemTotal
				+ ", cartItemPromoSavings=" + cartItemPromoSavings
				+ ", shippingTotal=" + shippingTotal
				+ ", shippingPromoSavings=" + shippingPromoSavings
				+ ", cartTotal=" + cartTotal + ", shoppingCartItemList="
				+ shoppingCartItemList + "]";
	}
}
