package com.redhat.coolstore.service;

import com.redhat.coolstore.model.ShoppingCart;
import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.jsonb.JsonbProducer;
import org.eclipse.microprofile.reactive.messaging.impl.ReactiveStreams;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

public class ShoppingCartOrderProcessor {

    @Log
    Log log;

    @Inject
    @ReactiveStreams("orders")
    ReactiveStreams.Publisher<String> topicEmitter;

    @Inject
    JsonbProducer jsonb;

    public void process(ShoppingCart cart) {
        String jsonString = jsonb.toJson(cart);
        log.info("Sending order from processor: " + jsonString);
        topicEmitter.send(jsonString);
    }

}