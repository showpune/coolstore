package com.redhat.coolstore.service;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.OnMessage;
import io.smallrye.reactive.messaging.providers.connectors.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InventoryNotificationMDB {

    private static final Logger LOGGER = Logger.getLogger(InventoryNotificationMDB.class);

    @Inject
    @Channel("HELLOWORLDMDBTopic")
    Emitter<String> topicEmitter;

    @OnMessage
    public Uni<Void> onMessage(String message) {
        LOGGER.info("Received message: " + message);
        return topicEmitter.emit(message);
    }

    @Incoming("HELLOWORLDMDBTopic")
    public void receive(String message) {
        // Handle incoming message
    }
}