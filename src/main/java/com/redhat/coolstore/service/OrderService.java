package com.redhat.coolstore.service;

import com.redhat.coolstore.model.Order;
import java.util.List;
import jakarta.inject.Inject; // Aligned with Quarkus' use of CDI
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import javax.enterprise.context.ApplicationScoped; // Import for Quarkus CDI

@ApplicationScoped // Use ApplicationScoped to denote a bean that is globally unique and thread-safe
public class OrderService {

  @Inject
  private EntityManager em;

  @Transactional
  public void save(Order order) {
    em.persist(order);
  }

  @Transactional
  public List<Order> getOrders() {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Order> criteria = cb.createQuery(Order.class);
    Root<Order> member = criteria.from(Order.class);
    criteria.select(member);
    return em.createQuery(criteria).getResultList();
  }

  @Transactional
  public Order getOrderById(long id) {
    return em.find(Order.class, id);
  }
}