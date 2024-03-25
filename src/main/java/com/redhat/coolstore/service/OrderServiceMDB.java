package com.redhat.coolstore.service;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import javax.enterprise.context.ApplicationScoped;
import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;
import javax.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class OrderService {
    
    @Inject
    @Channel("orders-out")
    Emitter<String> orderEmitter;

    @Incoming("orders-in")
    @Outgoing("processing-orders")
    @Broadcast
    public String processOrder(String order) {
        // Process the order here
        return order;
    }
}