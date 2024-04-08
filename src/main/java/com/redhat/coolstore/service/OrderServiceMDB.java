import jakarta.ejb.ApplicationScoped;
import jakarta.enterprise.inject.Inject;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import com.redhat.coolstore.model.Order;
import com.redhat.coolstore.utils.Transformers;

import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.annotations.Component;
import org.eclipse.microprofile.reactive.messaging.annotations.Incoming;
import org.eclipse.microprofile.reactive.messaging.annotations.Produces;
import org.eclipse.microprofile.reactive.messaging.annotations.Streamed;

@Component
public class OrderServiceMDB {

    @Inject
    OrderService orderService;

    @Inject
    CatalogService catalogService;

    @Incoming("topic/orders")
    @Produces("orders")
    @Streamed
    public void onMessage(Message<String> message) {
        String orderStr = message.getBody();
        Order order = Transformers.jsonToOrder(orderStr);
        orderService.save(order);
        order.getItemList().forEach(orderItem -> {
            catalogService.updateInventoryItems(orderItem.getProductId(), orderItem.getQuantity());
        });
    }
}
