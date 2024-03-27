package com.redhat.coolstore.utils;

import jakarta.inject.Inject;
import io.quarkus.logging.Log;

public class StartupListener {

    @Inject
    Log log;

    public void onStart() {
        log.info("AppListener(onStart)");
    }

    public void onStop() {
        log.info("AppListener(onStop)");
    }

}