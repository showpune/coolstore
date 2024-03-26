package com.redhat.coolstore.utils;

import jakarta.persistence.EntityManager;
import org.jboss.logging.Logger;
import jakarta.inject.Inject;

public class DatabaseMigrationStartup {

    @Inject
    Logger log;

    @Inject
    EntityManager em;

    public void migrateDatabase() {
        // Use em to migrate the database
        log.info("Migrating database");
    }
}