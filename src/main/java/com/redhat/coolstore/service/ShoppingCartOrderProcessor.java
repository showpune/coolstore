// Update the import statements
import io.quarkus.jms.JmsComponent;
import io.quarkus.micrometer.deployment.MicrometerInitializer;
import org.apache.camel.component.jms.JmsComponent.JmsComponentAutoAcknowledge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quarkus.arc.Inject;
import quarkus.micrometer.runtime.Micrometer;

// Update the `ShoppingCartOrderProcessor` class
import static io.quarkus.arc.Arc.container;
import static io.quarkus.micrometer.runtime.MicrometerTracing.tracer;
import static org.apache.camel.component.jms.JmsComponent.jmsComponentAutoAcknowledge;

@Quarkus
@RequestScoped
public class ShoppingCartOrderProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartOrderProcessor.class);

    @Inject
    private ShoppingCartService shoppingCartService;

    @Inject
    @JmsComponent(JmsComponentAutoAcknowledge.AUTO_ACKNOWLEDGE)
    private Emitter<String> topicEmitter;

    @Inject
    private MessagingTemplate messagingTemplate;

    @Inject
    private Session session;

    @Inject
    private Queue<String> queue;

    // Update the `createShoppingCartOrder` method
    public Order createShoppingCartOrder(UUID customerId) {
        return shoppingCartService.createOrder(customerId);
    }

    // Update the `getShoppingCartOrder` method
    public void getShoppingCartOrder(UUID sessionId) {
        if (sessionId == null) {
            return;
        }

        // Create a message listener for the queue
        topicEmitter.onMessage(session.createConsumer(queue));
    }

    // Add the Micrometer Initializer to enable tracing
    @ApplicationScoped
    public static class MicrometerInitializer extends MicrometerInitializer {
        @Override
        protected void configure() {
            tracer().name("shopping-cart-order-processor").addTags("http-method", "GET", "path", "/shopping-cart/order/{sessionId}");
        }
    }
}