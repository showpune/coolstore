package com.redhat.coolstore.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class ProductRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Product findProductById(Long id) {
        return entityManager.find(Product.class, id);
    }

    // Additional repository methods here
}