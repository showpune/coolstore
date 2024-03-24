@ApplicationScoped
public class OrderService implements OrderServiceRemote, OrderServiceLocal {

    @Inject
    @Qualifier("orderService")
    private ExtendedContext extendedContext;

    @Inject
    private OrderRepository orderRepository;

    @Override
    public void create(Order order) throws CreateException {
        // Add code to create an order
    }

    @Override
    public void delete(Long orderId) throws DeleteException {
        // Add code to delete an order
    }

    @Override
    public List<Order> findAll() throws FindAllException {
        // Add code to find all orders
    }

    @Override
    public Order findById(Long orderId) throws FindByIdException {
        // Add code to find an order by id
    }

    // Add other methods as needed
}