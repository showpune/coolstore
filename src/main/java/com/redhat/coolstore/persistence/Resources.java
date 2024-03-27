// Update the Resources class as follows
package com.redhat.coolstore.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Dependent
public class Resources {

    @PersistenceContext
    private EntityManager em;

    // Update the getter method as follows
    @Produces
    @Named("entityManager")
    public EntityManager getEntityManager() {
        return em;
    }
}