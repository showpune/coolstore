// Write the updated file for Quarkus in this section
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import io.quarkus.hibernate.orm.entity.Entity;
import io.quarkus.hibernate.orm.jpa.JpaEntityManager;
import com.redhat.coolstore.model.Order;
import com.redhat.coolstore.service.OrderService;

@RequestScoped
@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderEndpoint {

    private static final long serialVersionUID = -7227732980791688774L;

    @Inject
    private OrderService orderService;

    @GET
    @Path("/")
    public List<Order> listAll() {
        return orderService.getOrders();
    }

    @GET
    @Path("/{orderId}")
    public Order getOrder(@PathParam("orderId") long orderId) {
        return orderService.getOrderById(orderId);
    }
}