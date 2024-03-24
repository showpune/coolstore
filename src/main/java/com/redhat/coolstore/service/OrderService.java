@ApplicationScoped
public class OrderService implements OrderServiceInterface {

    @Inject
    private OrderRepository orderRepository;

    @Inject
    @ExtendedContext
    private Environment environment;

    @Override
    public List<Order> findAll() {
        // Add code to fetch orders from the repository and return a list
    }
}