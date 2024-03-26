// Update the import statements
import io.quarkus.jms.client.JakartaMessagingContext;
import io.quarkus.jms.client.JakartaTopic;
import io.quarkus.jms.client.MessageConsumer;
import io.quarkus.jms.client.MessageProducer;
import org.apache.activemq.artemis.api.core.Message;
import org.apache.activemq.artemis.api.core.SimpleStringBuilder;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.MessageReference;
import org.apache.activemq.artemis.core.server.impl.ServerSessionImpl;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQSession;
import org.apache.activemq.artemis.jms.client.MessageConsumer;
import org.apache.activemq.artemis.jms.client.MessageProducer;

// Update the `Topic` class
import org.apache.activemq.artemis.jms.client.JakartaTopic;

// Update the `JMSContext` class
import org.apache.activemq.artemis.jms.client.JakartaMessagingContext;
import org.apache.activemq.artemis.jms.client.MessageReference;
import org.apache.activemq.artemis.jms.client.ServerSession;

// Update the `MessageReference` class
import org.apache.activemq.artemis.jms.client.MessageReference;

// Update the `SimpleString` class
import org.apache.activemq.artemis.core.server.impl.SimpleStringBuilder;

// Update the `ServerSession` class
import org.apache.activemq.artemis.core.server.impl.ServerSessionImpl;

// Update the `ActiveMQConnectionFactory` class
import org.apache.activemq.artemis.ActiveMQConnectionFactory;

// Update the `ActiveMQSession` class
import org.apache.activemq.artemis.ActiveMQSession;

// Update the `MessageConsumer` class
import org.apache.activemq.artemis.jms.client.MessageConsumer;

// Update the `MessageProducer` class
import org.apache.activemq.artemis.jms.client.MessageProducer;

// Update the `ShoppingCartOrderProcessor` class
import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.utils.Transformers;

import javax.jms.MessageListener;
import javax.jms.Topic;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Dependent
public class ShoppingCartOrderProcessor {

    private static final Logger log = Logger.getLogger(ShoppingCartOrderProcessor.class.getName());

    @Inject
    private JakartaMessagingContext context;

    @Inject
    private JakartaTopic ordersTopic;

    public void process(ShoppingCart cart) {
        log.info("Sending order from processor: ");
        Message message = context.createProducer().createMessage();
        message.setBody(Transformers.shoppingCartToJson(cart));
        message.setDestination(ordersTopic);

        // Update the `MessageListener` to use the `Emitter` and `Channel` abstraction
        MessageListener messageListener = message -> {
            log.info("Order sent successfully: " + message.getBodyAsString());
        };

        // Update the `ordersTopic` to use the `Channel` abstraction
        ordersTopic.addMessageListener(messageListener);

        // Alternatively, you can use the `sendAndReceive()` method of the `JakartaMessagingContext` to send and receive messages in a single operation.
        // context.sendAndReceive(message, messageListener);
    }
}