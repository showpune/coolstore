package com.redhat.coolstore.service;

import java.util.logging.Logger;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.Channel;
import io.smallrye.reactive.messaging.Emitter;
import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.utils.Transformers;

@ApplicationScoped
public class ShoppingCartOrderProcessor  {

    @Inject
    Logger log;


    @Inject
    private transient JMSContext context;

    @Resource(lookup = "java:/topic/orders")
    @Channel("orders")
    Emitter<String> ordersEmitter;

    
  
    public void  process(ShoppingCart cart) {
        log.info("Sending order from processor: ");
        Multi.createFrom().item(Transformers.shoppingCartToJson(cart))
            .subscribe().with(ordersEmitter::send);
    }



}
