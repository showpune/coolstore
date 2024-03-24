// Update the StartupListener class to use jakarta.inject
package com.redhat.coolstore.utils;

import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.AfterInjection;
import jakarta.enterprise.inject.spi.BeforeInjection;
import org.slf4j.Logger;

import javax.inject.Named;
import java.util.logging.Logger;

@ApplicationScoped
public class StartupListener {

    @Inject
    @Named("log")
    Logger log;

    // Update the postStart method to use jakarta.inject
    @AfterInjection
    public void afterInjection() {
        log.info("AppListener(postStart)");
    }

    // Update the preStop method to use jakarta.inject
    @BeforeInjection
    public void beforeInjection(@Observes BeforeInjection event) {
        log.info("AppListener(preStop)");
    }
}