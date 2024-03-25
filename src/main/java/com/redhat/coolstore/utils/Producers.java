package com.redhat.coolstore.utils;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LoggingUtility {

    @Inject
    Logger logger;

    public void logMessage(String message) {
        logger.info(message);
    }
}