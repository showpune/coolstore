package com.redhat.coolstore.service;

import com.redhat.coolstore.model.Order;
import io.quarkus.arc.Injectable;
import io.quarkus.smallrye.reactivemessaging.api.ReactiveMessage;
import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Incoming;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class InventoryNotificationMDB {

    private static final int LOW_THRESHOLD = 50;

    @Inject
    CatalogService catalogService;

    @ConfigProperty(name = "quarkus.smallrye.reactive-messaging.inventory-notifier")
    String inventoryNotifierChannel;

    @Inject
    @Channel("orderList")
    ReactiveMessage<Order> orderList;

    private final Logger logger = Logger.getLogger(InventoryNotificationMDB.class);

    @Incoming(inventoryNotifierChannel)
    public void receiveMessage(JsonObject message) {
        JsonObject jsonObject = message;
        JsonReader jsonReader = Json.createReader(jsonObject.getJsonReader(Json.CREATOR));
        Order order = jsonReader.readObject().getJsonObject("order");
        order.getItemList().forEach(orderItem -> {
            int old_quantity = catalogService.getCatalogItemById(orderItem.getProductId()).getInventory().getQuantity();
            int new_quantity = old_quantity - orderItem.getQuantity();
            if (new_quantity < LOW_THRESHOLD) {
                logger.info("Inventory for item " + orderItem.getProductId() + " is below threshold (" + LOW_THRESHOLD + "), contact supplier!");
            } else {
                orderItem.setQuantity(new_quantity);
            }
        });
        orderList.accept(order);
    }
}