// Update the file as follows
import io.quarkus.jms.MessageListenerContainer;
import io.quarkus.jms.runtime.MessageListenerEndpoint;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.jms.DeliveryMode;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.redhat.coolstore.model.Order;
import com.redhat.coolstore.model.OrderItem;
import com.redhat.coolstore.service.CatalogService;
import com.redhat.coolstore.service.OrderService;
import com.redhat.coolstore.utils.QuarkusTransforms;
import io.quarkus.jms.MessageListenerEndpointConfig;

@ApplicationScoped
public class OrderServiceMDB {

    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceMDB.class);

    @Inject
    private OrderService orderService;

    @Inject
    private CatalogService catalogService;

    @MessageListenerEndpointConfig
    @MessageListenerContainer(maxConcurrentConsumers = 5)
    @Incoming
    public void onMessage(@Incoming Message message) {
        LOG.info("Message recd {}", message);
        ObjectMessage objectMessage = (ObjectMessage) message;
        Order order = objectMessage.getBody(Order.class);
        LOG.info("Received order: {}", order);
        orderService.save(order);
        List<OrderItem> orderItems = order.getItemList();
        for (OrderItem orderItem : orderItems) {
            catalogService.updateInventoryItems(orderItem.getProductId(), orderItem.getQuantity());
        }
    }

    @QuarkusService
    public static class OrderService {
        @Incoming
        @MessageListener(messageClass = Order.class)
        public void onMessage(@Incoming Order order) {
            LOG.info("Received order: {}", order);
            orderService.save(order);
            List<OrderItem> orderItems = order.getItemList();
            for (OrderItem orderItem : orderItems) {
                catalogService.updateInventoryItems(orderItem.getProductId(), orderItem.getQuantity());
            }
        }
    }

    @QuarkusEntity
    public static class Order {
        @Id
        private String id;
        private String customerName;
        private String address;
        @ElementCollection
        private List<OrderItem> itemList = new ArrayList<>();

        public Order() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public List<OrderItem> getItemList() {
            return itemList;
        }

        public void setItemList(List<OrderItem> itemList) {
            this.itemList = itemList;
        }
    }

    @QuarkusService
    public static class CatalogService {
        @Inject
        private OrderService orderService;

        // Add any required methods here
    }

    @MessageListener(messageClass = String.class)
    public void onMessage(@Incoming Message message) {
        LOG.info("Message recd {}", message);
        // Handle other message types here
    }
}