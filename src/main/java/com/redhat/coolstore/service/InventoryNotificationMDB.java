// Update the InventoryNotificationMDB class as follows

import io.quarkus.arc.Arc;
import io.quarkus.jms.client.MessageTopic;
import io.quarkus.jms.client.ConnectionFactory;
import io.quarkus.jms.client.Session;
import io.quarkus.jms.client.Subscriber;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.jboss.logging.Logger;
import com.redhat.coolstore.model.Order;
import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.service.CatalogService;
import com.redhat.coolstore.utils.Transformers;
import io.quarkus.arc.Inject;
import io.quarkus.runtime.annotations.QuarkusApplication;
import io.quarkus.runtime.configuration.ConfigItem;
import io.quarkus.runtime.configuration.ConfigItems;
import io.quarkus.runtime.configuration.Configurers;
import io.quarkus.runtime.configuration.FeatureConfig;
import io.quarkus.runtime.configuration.ResourceConfig;
import io.quarkus.runtime.configuration.ServerConfig;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

@QuarkusApplication(name = "coolstore-service", config = ConfigItems.SERVER_CONFIG + "/coolstore-service.yml")
public class InventoryNotificationMDB {

    private static final Logger log = Logger.getLogger(InventoryNotificationMDB.class);

    @Inject
    private CatalogService catalogService;

    @Inject
    private Transformers transformers;

    @Inject
    private ActiveMQServer activeMQServer;

    @Inject
    private ConnectionFactory connectionFactory;

    @Inject
    private Session session;

    @Inject
    private Subscriber subscriber;

    @Inject
    private MessageTopic<String> messageTopic;

    public static void main(String[] args) {
        Configurers.configure(InventoryNotificationMDB.class, ConfigItems.SERVER_CONFIG + "/coolstore-service.yml");
    }

    public void onMessage(String message) {
        // Handle the message here
        log.info("received message inventory");
        List<Order> orders = transformers.jsonToOrder(message);
        for (Order order : orders) {
            int oldQuantity = catalogService.getCatalogItemById(order.getProductId()).getInventory().getQuantity();
            int newQuantity = oldQuantity - order.getQuantity();
            if (newQuantity < LOW_THRESHOLD) {
                log.info("Inventory for item " + order.getProductId() + " is below threshold (" + LOW_THRESHOLD + "), contact supplier!");
            } else {
                order.setQuantity(newQuantity);
            }
        }
    }

    public void init() {
        Context ctx = new InitialContext();
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(activeMQServer.getConnectorFactory());
        connectionFactory = cf;
        session = connectionFactory.createSession(false, Session.AUTO_ACKNOWLEDGE);
        subscriber = session.createSubscriber(messageTopic);
        subscriber.setMessageListener(this);
        messageTopic.setMessageListener(this::onMessage);
    }

    public void close() {
        subscriber.close();
        session.close();
        connectionFactory.close();
    }
}
