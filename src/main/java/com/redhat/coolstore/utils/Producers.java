package com.redhat.coolstore.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import java.util.logging.Logger;

@ApplicationScoped
public class Producers {

    Logger log = Logger.getLogger(Proaders.class.getName());

    @Singleton
    public Logger produceLog() {
        return Logger.getLogger(this.getClass().getName());
    }

}