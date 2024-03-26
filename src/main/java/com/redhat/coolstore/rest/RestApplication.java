// Update the RestApplication class to extend QuarkusApplication and add the ApplicationPath annotation
import org.jboss.quarkus.resteasy.spi.ApplicationPathProvider;
import org.jboss.quarkus.resteasy.spi.PathResource;
import io.quarkus.resteasy.runtime.QuarkusApplication;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("/")
@QuarkusApplication(ApplicationPath = "/")
public class RestApplication {

    private static final Logger logger = Logger.getLogger(RestApplication.class.getName());

    @Path("/products")
    public static class ProductsResource {

        @GET
        @Path("/{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getProductById(@PathParam("id") Long id) {
            logger.info("Getting product with id: " + id);
            return Response.ok(new Product(id, "Product " + id)).build();
        }

        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        public Response createProduct(Product product) {
            logger.info("Creating product: " + product);
            return Response.created(URI.create("/products/" + product.getId())).build();
        }

        @PUT
        @Path("/{id}")
        @Consumes(MediaType.APPLICATION_JSON)
        public Response updateProduct(@PathParam("id") Long id, Product product) {
            logger.info("Updating product with id: " + id + " to: " + product);
            return Response.ok().build();
        }

        @DELETE
        @Path("/{id}")
        public Response deleteProduct(@PathParam("id") Long id) {
            logger.info("Deleting product with id: " + id);
            return Response.noContent().build();
        }
    }
}