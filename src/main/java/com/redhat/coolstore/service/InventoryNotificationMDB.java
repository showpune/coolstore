package com.redhat.coolstore.service;

import com.redhat.coolstore.model.Order;
import com.redhat.coolstore.utils.Transformers;
import io.smallrye.reactive.messaging.kafka.KafkaConnectionFactory;
import io.smallrye.reactive.messaging.kafka.KafkaEmitter;
import io.smallrye.reactive.messaging.kafka.KafkaSession;
import io.smallrye.reactive.messaging.kafka.KafkaTopic;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import reactor.core.publisher.Mono;

import java.util.List;

public class InventoryNotificationMDB {

    private static final int LOW_THRESHOLD = 50;

    @Inject
    private CatalogService catalogService;

    @Inject
    @KafkaTopic("orders")
    KafkaTopic<String> ordersTopic;

    @Inject
    @Outgoing("HELLOWORLDMDBTopic")
    KafkaEmitter<String> topicEmitter;

    @Incoming("HELLOWORLDMDBTopic")
    public void onMessage(String message) {
        System.out.println("received message inventory");
        Order order = Transformers.jsonToOrder(message);
        List<Order.OrderItem> orderItems = order.getItemList();
        orderItems.forEach(orderItem -> {
            int old_quantity = catalogService.getCatalogItemById(orderItem.getProductId()).getInventory().getQuantity();
            int new_quantity = old_quantity - orderItem.getQuantity();
            if (new_quantity < LOW_THRESHOLD) {
                System.out.println("Inventory for item " + orderItem.getProductId() + " is below threshold (" + LOW_THRESHOLD + "), contact supplier!");
                topicEmitter.send(Mono.just("Inventory for item " + orderItem.getProductId() + " is below threshold (" + LOW_THRESHOLD + "), contact supplier!"));
            } else {
                orderItem.setQuantity(new_quantity);
            }
        });
    }

    @Inject
    public void init(KafkaConnectionFactory connectionFactory, KafkaSession session) {
        ordersTopic.subscribe(this::onMessage);
        session.start();
    }

    public void close() {
        ordersTopic.unsubscribe();
        session.close();
    }
}
