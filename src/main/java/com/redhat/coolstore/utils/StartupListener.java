package com.redhat.coolstore.utils;

import io.quarkus.arc.runtime.ApplicationLifecycle;
import jakarta.inject.Inject;
import java.util.logging.Logger;

public class StartupListener implements ApplicationLifecycle {

    @Inject
    Logger log;

    @Override
    public void initialize() {
        // This method is called when the CDI container is initialized.
    }

    @Override
    public void performStop() throws Exception {
        // This method is called when the CDI container is being stopped.
        log.info("AppListener(preStop)");
    }

    @Override
    public void performStart() throws Exception {
        // This method is called when the CDI container is being started.
        log.info("AppListener(postStart)");
    }

}
