package com.redhat.coolstore.service;

import javax.inject.ApplicationScoped;
import javax.inject.Inject;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MathContext;
import javax.money.BigDecimalMath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@ApplicationScoped
public class ShippingService {

    @GET
    @Path("/calculate-shipping")
    public MonetaryAmount calculateShipping(@QueryParam("cartItemTotal") double cartItemTotal) {
        if (cartItemTotal >= 0 && cartItemTotal < 25) {
            return Monetary.amount(2.99);
        } else if (cartItemTotal >= 25 && cartItemTotal < 50) {
            return Monetary.amount(4.99);
        } else if (cartItemTotal >= 50 && cartItemTotal < 75) {
            return Monetary.amount(6.99);
        } else if (cartItemTotal >= 75 && cartItemTotal < 100) {
            return Monetary.amount(8.99);
        } else if (cartItemTotal >= 100 && cartItemTotal < 10000) {
            return Monetary.amount(10.99);
        }
        return Monetary.amount(0);
    }

    @GET
    @Path("/calculate-insurance")
    public MonetaryAmount calculateInsurance(@QueryParam("cartItemTotal") double cartItemTotal) {
        MonetaryAmount insuranceCost;
        if (cartItemTotal >= 25 && cartItemTotal < 100) {
            insuranceCost = Monetary.amount(cartItemTotal).multiply(Monetary.getAmountFactory(Monetary.getCurrency("USD")).setContext(new MathContext(2))).divide(100, new MathContext(2));
        } else if (cartItemTotal >= 100 && cartItemTotal < 500) {
            insuranceCost = Monetary.amount(cartItemTotal).multiply(Monetary.getAmountFactory(Monetary.getCurrency("USD")).setContext(new MathContext(2))).divide(100, new MathContext(2));
        } else if (cartItemTotal >= 500 && cartItemTotal < 10000) {
            insuranceCost = Monetary.amount(cartItemTotal).multiply(Monetary.getAmountFactory(Monetary.getCurrency("USD")).setContext(new MathContext(2))).divide(100, new MathContext(2));
        } else {
            insuranceCost = Monetary.amount(0);
        }
        return insuranceCost;
    }

}