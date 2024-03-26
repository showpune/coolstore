import jakarta.inject.Inject;

// other imports and code

@ApplicationScoped
public class OrderService {

    @Inject
    EntityManager em;

    // other code

    public void createOrder(Order order) {
        // CriteriaBuilder cb = em.getCriteriaBuilder(); // remove this line
        // other code
    }
}