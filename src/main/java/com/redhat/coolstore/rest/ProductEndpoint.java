package com.redhat.coolstore.rest;

import java.util.List;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import io.quarkus.arc.inject.Inject;
import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.service.ProductService;

public class ProductEndpoint {

    @Inject
    private ProductService pm;

    @GET
    @Path("/")
    public List<Product> listAll() {
        return pm.getProducts();
    }

    @GET
    @Path("/{itemId}")
    public Product getProduct(@PathParam("itemId") String itemId) {
        return pm.getProductByItemId(itemId);
    }
}