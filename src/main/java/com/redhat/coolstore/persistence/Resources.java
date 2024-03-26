package com.redhat.coolstore.persistence;

import javax.inject.Inject;
import javax.inject.Qualifier;

public class Resources {

    @Inject
    @ExtendedContext
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }
}

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtendedContext {
}