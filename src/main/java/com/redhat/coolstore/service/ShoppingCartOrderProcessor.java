package com.redhat.coolstore.service;

import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Provider;
import jakarta.jms.Message;
import jakarta.jms.MessageProducer;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;

import com.redhat.coolstore.model.ShoppingCart;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class ShoppingCartOrderProcessor {

    @Inject
    Logger log;

    @Inject
    @Named("ordersTopic")
    Provider<Emitter<ShoppingCart>> topicEmitter;

    @PostConstruct
    void init() {
        log.info("Initializing ShoppingCartOrderProcessor");
    }

    @Incoming("orders")
    public Uni<Void> process(ShoppingCart cart) {
        log.info("Sending order from processor: ");
        return Uni.createFrom().item(cart)
                .map(shoppingCart -> {
                    MessageProducer producer = topicEmitter.get().get().getSender();
                    TextMessage message = (TextMessage) producer.createTextMessage();
                    message.setText(Transformers.shoppingCartToJson(shoppingCart));
                    producer.send();
                    return null;
                });
    }

}
