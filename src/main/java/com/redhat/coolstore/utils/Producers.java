package com.redhat.coolstore.utils;

import jakarta.enterprise.inject.Inject;
import jakarta.enterprise.inject.Model;
import jakarta.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

@Model
public class Producers {

    @Inject
    Logger log;

    public Logger getLogger(InjectionPoint injectionPoint) {
        String className = injectionPoint.getMember().getDeclaringClass().getName();
        if (log.isLoggable(Level.FINEST)) {
            log.finest("Logger for class: " + className);
        }
        return Logger.getLogger(className);
    }

}
