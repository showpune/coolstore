package com.redhat.coolstore.utils;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class StartupListener {

    @Inject
    Logger log;

    void onStart(@Observes StartupEvent ev) {
        log.info("AppListener(startup)");
    }

    // If preStop behavior is necessary, consider using Quarkus' @PreDestroy in a bean
    // However, Quarkus encourages graceful shutdown and cleanup via the standard CDI @PreDestroy annotation
}