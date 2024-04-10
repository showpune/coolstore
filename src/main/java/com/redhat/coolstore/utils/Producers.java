// Update the file as follows

package com.redhat.coolstore.utils;

import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

public class Producers {

    Logger log = Logger.getLogger(Producers.class.getName());

    @Produces
    @Named
    public Logger produceLog(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

}
