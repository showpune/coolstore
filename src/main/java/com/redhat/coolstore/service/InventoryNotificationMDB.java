import io.quarkus.jms.client.TopicConnectionFactory;
import io.quarkus.jms.client.TopicSession;
import io.quarkus.micrometer.runtime.Metric;
import io.quarkus.micrometer.runtime.Timer;
import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.core.server.impl.ServerImpl;
import org.jboss.logging.Logger;
import com.redhat.coolstore.model.Order;
import com.redhat.coolstore.utils.Transformers;
import io.quarkus.arc.Arc;
import io.quarkus.jms.client.MessageListener;
import io.quarkus.runtime.annotations.RegisterGenerated;
import io.quarkus.vertx.http.client.HttpClient;
import io.smallrye.reactive.messaging.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Topic;
import org.apache.activemq.artemis.api.core.Message;
import org.apache.activemq.artemis.api.core.SimpleStringFactory;
import org.apache.activemq.artemis.core.server.MessageReference;
import org.apache.activemq.artemis.core.server.impl.ServerSession;
import org.jboss.logging.Logger;

@RegisterGenerated
public class InventoryNotificationMDB implements MessageListener {
    private static final int LOW_THRESHOLD = 50;

    @Inject
    private CatalogService catalogService;

    private final static SimpleStringFactory SSF = new SimpleStringFactory();
    private final static String JMS_FACTORY = "quarkus-jms-client-runtime";
    private final static String TOPIC = "orders";
    private TopicConnectionFactory tconFactory;
    private TopicSession tsession;
    private TopicSubscriber tsubscriber;
    private final Logger log = Logger.getLogger(InventoryNotificationMDB.class);

    @Override
    public void onMessage(Message message) {
        TextMessage msg;
        try {
            System.out.println("received message inventory");
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                String orderStr = msg.getBody(String.class);
                Order order = Transformers.jsonToOrder(orderStr);
                order.getItemList().forEach(orderItem -> {
                    int old_quantity = catalogService.getCatalogItemById(orderItem.getProductId()).getInventory().getQuantity();
                    int new_quantity = old_quantity - orderItem.getQuantity();
                    if (new_quantity < LOW_THRESHOLD) {
                        log.warn("Inventory for item " + orderItem.getProductId() + " is below threshold (" + LOW_THRESHOLD + "), contact supplier!");
                    } else {
                        orderItem.setQuantity(new_quantity);
                    }
                });
            }
        } catch (JMSException jmse) {
            log.error("An exception occurred: " + jmse.getMessage());
        }
    }

    @Inject
    public InventoryNotificationMDB(HttpClient httpClient) {
        // Initialize the JMS connection factory and topic
        tconFactory = Arc.container().select(HttpClient.class).get().createTopicConnectionFactory(JMS_FACTORY);
        Topic topic = tconFactory.createTopic(TOPIC);

        // Initialize the TopicSession and TopicSubscriber
        tsession = tconFactory.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
        tsubscriber = tsession.createSubscriber(topic);
        tsubscriber.setMessageListener(this);

        // Start the TopicConnection
        tconFactory.start();
    }

    @Override
    public void onMessage(Message message) {
        // Handle the message here
    }

    @Override
    public void close() {
        tsubscriber.close();
        tsession.close();
        tconFactory.close();
    }
}