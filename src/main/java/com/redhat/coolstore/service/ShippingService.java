package com.redhat.coolstore.service;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.math.BigDecimal;

@Path("/shipping")
public class ShippingService {

    @Inject
    ShoppingCart shoppingCart;

    @GET
    @Path("/calculateShipping")
    public BigDecimal calculateShipping() {
        if (shoppingCart != null) {
            double cartItemTotal = shoppingCart.getCartItemTotal();
            BigDecimal shippingCost;

            if (cartItemTotal >= 0 && cartItemTotal < 25) {
                shippingCost = BigDecimal.valueOf(2.99);
            } else if (cartItemTotal >= 25 && cartItemTotal < 50) {
                shippingCost = BigDecimal.valueOf(4.99);
            } else if (cartItemTotal >= 50 && cartItemTotal < 75) {
                shippingCost = BigDecimal.valueOf(6.99);
            } else if (cartItemTotal >= 75 && cartItemTotal < 100) {
                shippingCost = BigDecimal.valueOf(8.99);
            } else if (cartItemTotal >= 100 && cartItemTotal < 10000) {
                shippingCost = BigDecimal.valueOf(10.99);
            } else {
                shippingCost = BigDecimal.ZERO;
            }

            return shippingCost;
        }

        return BigDecimal.ZERO;
    }

    @GET
    @Path("/calculateShippingInsurance")
    public BigDecimal calculateShippingInsurance(@QueryParam("cartItemTotal") double cartItemTotal) {
        if (cartItemTotal >= 25 && cartItemTotal < 100) {
            return getPercentOfTotal(cartItemTotal, 0.02);
        } else if (cartItemTotal >= 100 && cartItemTotal < 500) {
            return getPercentOfTotal(cartItemTotal, 0.015);
        } else if (cartItemTotal >= 500 && cartItemTotal < 10000) {
            return getPercentOfTotal(cartItemTotal, 0.01);
        }

        return BigDecimal.ZERO;
    }

    private static BigDecimal getPercentOfTotal(double value, double percentOfTotal) {
        return BigDecimal.valueOf(value * percentOfTotal)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
