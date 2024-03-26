// Update the CatalogService to use Quarkus JMS client and CDI scope annotation
import io.quarkus.jms.JmsClient;
import io.quarkus.jms.MessageListenerContainer;
import org.apache.qutee.jms.QuteeJmsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.redhat.coolstore.domain.Order;
import com.redhat.coolstore.service.OrderService;
import io.quarkus.arc.Arc;
import io.quarkus.jms.JmsClient;

import javax.inject.Inject;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.UUID;

@QuarkusBean
public class CatalogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogService.class);

    @Inject
    private OrderService orderService;

    public void updateInventoryItems(String productId, int quantity) {
        final Order order = new Order()
            .order(new Order.Items()
                .items(new Order.Items.Item()
                    .productId(productId)
                    .quantity(quantity)));

        final Message<Order> message = new ObjectMessage<>(order);

        final JmsClient jmsClient = JmsClient.builder()
            .connectionFactory("java:/JmsConnectionFactory")
            .build();

        final MessageListenerContainer messageListenerContainer = jmsClient.createMessageListenerContainer();
        messageListenerContainer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    LOGGER.info("Inventory item updated successfully.");
                } catch (JMSException e) {
                    LOGGER.error("Error updating inventory item", e);
                }
            }
        });

        messageListenerContainer.start();

        orderService.save(order);
    }
}