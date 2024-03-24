// Update the file to use Quarkus and replace the required imports and classes
import io.quarkus.arc.Arc;
import io.quarkus.jakartaee.deployment.ApplicationScoped;
import io.quarkus.messaging.amqp.JakartaAmqpClient;
import org.apache.qpid.proton.amqp.messaging.JakartaAmqpMessage;
import org.apache.qpid.proton.amqp.messaging.JakartaAmqpMessageProperties;
import org.jboss.logging.Logger;
import com.redhat.coolstore.domain.ShoppingCart;
import com.redhat.coolstore.service.ShoppingCartOrderProcessor;
import io.quarkus.gson.GsonProvider;

import static io.quarkus.arc.Arc.deployment.ArcContainer.DEFAULT_SCOPE;

@ApplicationScoped
@Path("/shopping-cart")
@RegisterRestService
public class ShoppingCartOrderProcessor {

    @Inject
    private Logger log;
    @Inject
    private GsonProvider gson;

    @Inject
    private JakartaAmqpClient quarkusAmqpClient;

    public void process(ShoppingCart cart) {
        log.info("Sending order from processor: ");
        String json = cartToJson(cart);
        JakartaAmqpMessage message = Message.createTextMessage(json.getBytes());
        JakartaAmqpMessageProperties messageProperties = JakartaAmqpMessageProperties.createTextProperties("application", "coolstore");
        quarkusAmqpClient.send("/topic/orders", message);
        log.info("Order sent successfully: " + cart.getId());
    }

    private String cartToJson(ShoppingCart cart) {
        return gson.toJson(cart);
    }
}