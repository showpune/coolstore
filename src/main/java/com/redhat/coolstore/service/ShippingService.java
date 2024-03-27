package com.redhat.coolstore.rest;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.service.ShippingService;

@Path("/shipping")
public class ShippingRestService {

    @Inject
    private ShippingService shippingService;

    @GET
    @Path("/calculate")
    public Response calculateShippingOrInsurance(String type) {
        BigDecimal cost;
        if (type.equalsIgnoreCase("shipping")) {
            cost = shippingService.calculateShipping();
        } else {
            cost = shippingService.calculateInsurance();
        }
        return Response.ok(cost).build();
    }

}