// OrderServiceMDB.java

import io.quarkus.jms.client.TopicConnectionFactory;
import io.quarkus.jms.client.QueueConnectionFactory;
import io.quarkus.jms.client.MessageListener;
import io.quarkus.jms.client.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.redhat.coolstore.model.Order;
import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.service.OrderService;
import com.redhat.coolstore.service.CatalogService;
import io.quarkus.vertx.http.client.QuarkusVertxHttpClient;
import io.quarkus.vertx.http.client.RequestOptions;
import io.quarkus.vertx.http.client.vertx.VertxHttpClient;

import static io.quarkus.jms.client.MessageListener.DEFAULT_DESTINATION_LOOKUP;
import static io.quarkus.jms.client.MessageListener.DEFAULT_DESTINATION_TYPE;
import static io.quarkus.jms.client.MessageListener.DEFAULT_ACKNOWLEDGE_MODE;

@QuarkusMessageDriven(name = "OrderServiceMDB", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "quarkus.jms.DEFAULT_DESTINATION_LOOKUP"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "quarkus.jms.DEFAULT_DESTINATION_TYPE"),
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "quarkus.jms.DEFAULT_ACKNOWLEDGE_MODE")})
public class OrderServiceMDB implements MessageListener<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceMDB.class);

    @Inject
    private OrderService orderService;

    @Inject
    private CatalogService catalogService;

    @Inject
    private VertxHttpClient quarkusVertxHttpClient;

    @Override
    public void onMessage(String message) {
        LOGGER.info("Message recd : {}", message);

        String orderStr = message;
        LOGGER.info("Received order: {}", orderStr);

        Order order = ObjectMapper.readValue(orderStr, Order.class);
        LOGGER.info("Order object is {}", order);

        List<Order.Item> items = new ArrayList<>();
        for (Product product : catalogService.getAllProducts()) {
            items.add(new Order.Item(product.getId(), product.getInventory(), product.getQuantity()));
        }

        order.setItemList(items);
        orderService.save(order);
    }

    @Inject
    private OrderService orderService;

    @Inject
    private CatalogService catalogService;

    @Inject
    private TopicConnectionFactory topicConnectionFactory;

    @Inject
    private QueueConnectionFactory queueConnectionFactory;
}