// Update the code as follows
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.reactor.core.publisher.Flux;
import org.reactor.core.publisher.Mono;
import reactor.http.client.HttpClient;
import reactor.http.client.Request;
import reactor.http.client.Response;
import reactor.http.client.reactive.ClientRequestContextReactor;
import reactor.http.client.reactive.HttpClientException;
import reactor.http.client.reactive.ReactorClient;
import reactor.util.annotation.Nullable;
import static java.util.stream.Collectors.collect;

import static quarkus.resteasy.path.PathVariable.pathVariable;
import static quarkus.resteasy.reactive.client.ClientRequestContext.DEFAULT;

@Quarkus
public class OrderEndpoint {

    private final ReactorClient<HttpClient> client;
    private final OrderService orderService;

    @Inject
    public OrderEndpoint(ReactorClient<HttpClient> client, OrderService orderService) {
        this.client = client.build();
        this.orderService = orderService;
    }

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Flux<Order> listAll() {
        return client.get()
            .uri("/api/orders")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(List.class)
            .map(orders -> orders.stream()
                .map(o -> Order.builder().id(o.getId()).customerName(o.getCustomerName()).orderDate(o.getOrderDate()).status(o.getStatus()).build())
                .collect(collect()));
    }

    @Path("/{orderId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Mono<Order> getOrder(@PathParam("orderId") Long orderId) {
        return client.get()
            .uri("/api/orders/{orderId}")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Order.class)
            .map(o -> Order.builder().id(o.getId()).customerName(o.getCustomerName()).orderDate(o.getOrderDate()).status(o.getStatus()).build());
    }
}