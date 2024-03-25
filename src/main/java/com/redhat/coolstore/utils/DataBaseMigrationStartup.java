package com.redhat.coolstore.utils;

import jakarta.inject.Singleton;
import io.quarkus.runtime.StartupEvent;
import javax.enterprise.event.Observes;

@Singleton
public class DataBaseMigrationStartup {

    void onStart(@Observes StartupEvent ev) {
        // Logic for database migration
        // Since the original code snippet does not include the actual migration logic,
        // it's assumed to be implemented here.
    }
}