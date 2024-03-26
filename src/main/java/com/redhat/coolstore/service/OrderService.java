@Quarkus(scopes = "application")
@Path("/order")
public class OrderService {

    @Inject
    private OrderRepository orderRepository;

    @Inject
    @ApplicationScoped
    private ExtendedContext extendedContext;

    @GET
    @Path("/{id}")
    public Response getOrder(@PathParam("id") Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + orderId + " not found"));

        return Response.ok(order).build();
    }
}