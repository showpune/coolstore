package com.redhat.coolstore.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class OrderService {

    @Inject
    OrderRepository orderRepository;

    public PanacheQuery<Order> findAllOrders(Integer page, Integer size) {
        return orderRepository.listAll(Sort.by("id"), Page.create(page, size));
    }

    public Long countOrders() {
        return orderRepository.count();
    }

    public Order findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Order createOrder(Order order) {
        order.persist();
        return order;
    }

    public Order updateOrder(Order order) {
        order.merge();
        return order;
    }
}