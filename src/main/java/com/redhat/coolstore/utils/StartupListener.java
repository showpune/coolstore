package com.redhat.coolstore.utils;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.ShutdownEvent;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

public class StartupListener {

    @Inject
    Logger log;

    void onStart(StartupEvent event) {
        log.info("AppListener(postStart)");
    }

    void onStop(ShutdownEvent event) {
        log.info("AppListener(preStop)");
    }

}