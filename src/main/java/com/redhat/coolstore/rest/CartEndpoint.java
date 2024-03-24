// src/main/java/com/redhat/coolstore/rest/CartEndpoint.java

import io.quarkus.hibernate.orm.runtime.entity.EntityManagerFactoryBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.redhat.coolstore.domain.Cart;
import com.redhat.coolstore.repository.CartRepository;
import io.quarkus.hibernate.orm.jpa.HibernateEntityManager;
import io.quarkus.jakarta.ws.rs.runtime.ComponentModel;
import io.quarkus.spring.annotations.QuarkusSpring;
import io.quarkus.util.Strings;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Path("/cart")
@QuarkusComponent
public class CartEndpoint {

    @Inject
    private CartRepository cartRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cart> getCarts() {
        return StreamSupport.stream(cartRepository.findAll().spliterator(), false)
            .map(cart -> cart.toBuilder().build())
            .collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCart(@QueryParam("id") String id, Cart cart) {
        if (Strings.isNullOrEmpty(id)) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Cart id cannot be empty").build();
        }

        if (cartRepository.findById(id) != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Cart with id '" + id + "' already exists").build();
        }

        return cartRepository.save(cart);
    }
}