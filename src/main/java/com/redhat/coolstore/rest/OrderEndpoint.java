// Update the file as follows
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.hibernate.SessionFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import com.redhat.coolstore.model.Order;
import com.redhat.coolstore.repository.OrderRepository;
import com.redhat.coolstore.service.OrderService;
import io.quarkus.hibernate.orm.runtime.SessionScope;
import io.quarkus.resteasy.reactive.common.JacksonProvider;
import io.quarkus.vertx.http.client.HttpClient;
import io.quarkus.vertx.http.client.reactive.HttpClientReactive;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class OrderEndpoint {

    private static final long serialVersionUID = 1L;

    @Inject
    private OrderRepository orderRepository;
    @Inject
    private OrderService orderService;
    @Inject
    private HttpClientReactive httpClient;
    @Inject
    @SessionScoped
    private SessionFactory sessionFactory;

    @GET
    public List<Order> listAll() {
        return orderRepository.findAll().stream()
                .map(o -> new Order(o.getId(), o.getCustomerName(), o.getTotal()))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{orderId}")
    public Optional<Order> getOrder(@PathParam("orderId") UUID orderId) {
        return orderRepository.findById(orderId)
                .map(o -> new Order(o.getId(), o.getCustomerName(), o.getTotal()));
    }

    @Inject
    public JacksonProvider jacksonProvider();

    @Inject
    public HttpClientReactive httpClientReactive();

    @Inject
    public void setHttpClient(HttpClientReactive httpClient) {
        this.httpClient = httpClient;
    }
}