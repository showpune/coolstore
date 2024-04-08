package com.redhat.coolstore.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@Dependent
public class Resources {

    @Inject
    EntityManager em;

    // Remove the getEntityManager() method if it is not needed
    // Replace the @Produces annotation with @Named and a getter method if it is needed
    // For example:
    //
    // @Named("em")
    // @Dependent
    // public EntityManager getEntityManager() {
    //     return em;
    // }
}
