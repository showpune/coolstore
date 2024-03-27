package com.redhat.coolstore.rest;

import jakarta.inject.Inject;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;

public class OrderEndpoint {

    @Inject
    private OrderService os;

    public Uni<TemplateInstance> listAll(Page page, Sort sort) {
        return Uni.createFrom().item(() -> os.listAll(page, sort))
                .onItem().transform(orders -> {
                    // Do something with the orders
                    return orders;
                });
    }

    public Uni<Order> getOrder(Long orderId) {
        return Uni.createFrom().item(() -> os.getOrderById(orderId));
    }

}