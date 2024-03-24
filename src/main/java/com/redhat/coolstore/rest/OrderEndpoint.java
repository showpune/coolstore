// Update the import statements
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

// Update the `Order` and `OrderService` classes
import model.Order;
import service.OrderService;

// Update the `OrderEndpoint` class
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/orders")
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