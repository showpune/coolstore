// Update the import statements
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Jwt;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;

// Update the package name
package com.redhat.coolstore.rest.product;

// Update the ProductEndpoint class
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Optional;

import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.service.ProductService;

@RequestScoped
@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductEndpoint {

    /**
     *
     */
    private static final long serialVersionUID = -7227732980791688773L;

    @Inject
    private ProductService pm;

    // Update the listAll method
    @GET
    @Path("/")
    public List<Product> listAll() {
        return pm.getProducts().stream()
            .map(p -> new Product(p.getId(), p.getName(), p.getDescription()))
            .collect(Collectors.toList());
    }

    // Update the getProduct method
    @GET
    @Path("/{itemId}")
    public Optional<Product> getProduct(@PathParam("itemId") String itemId) {
        return pm.getProductByItemId(itemId)
            .map(p -> new Product(p.getId(), p.getName(), p.getDescription()));
    }

    // Extract the getProductByItemId method into a separate utility class
    public class ProductById {

        private final ProductService pm;

        public ProductById(ProductService pm) {
            this.pm = pm;
        }

        @Inject
        public ProductById() {
            // Empty constructor for CDI injection
        }

        public Optional<Product> getProductById(String itemId) {
            return pm.getProductByItemId(itemId);
        }
    }
}