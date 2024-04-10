// Update the import statements accordingly
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

// Update the package name
import com.redhat.coolstore.model.Order;
import com.redhat.coolstore.service.OrderService;

// Update the class name
public class OrderEndpoint {

    // Update the import statements accordingly
    private static final long serialVersionUID = -7227732980791688774L;

    // Update the field name
    @Inject
    private OrderService os;

    // Update the method names and parameters
    @GET
    @Path("/")
    public List<Order> listAll() {
        return os.getOrders();
    }

    @GET
    @Path("/{orderId}")
    public Order getOrder(@PathParam("orderId") long orderId) {
        return os.getOrderById(orderId);
    }
}
