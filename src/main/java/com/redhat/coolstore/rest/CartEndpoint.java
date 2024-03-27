package com.redhat.coolstore.rest;

import com.redhat.coolstore.service.CartService;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.TemplateInstanceBuilder;
import io.quarkus.runtime.annotations.Inject;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/carts")
public class CartEndpoint {

    @Inject
    CartService cartService;

    @Inject
    @Template("carts.html")
    Template template;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getCart(@PathParam("id") String id) {
        return cartService.getCart(id)
                .onItem().transform(cart -> Response.ok(Map.of("cart", cart)).build());
    }

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getCarts() {
        TemplateInstanceBuilder builder = TemplateInstance.builder(template);
        return builder.data("carts", cartService.getCarts()).build();
    }
}