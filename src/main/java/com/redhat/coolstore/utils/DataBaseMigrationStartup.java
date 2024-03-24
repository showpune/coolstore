// Update the code as follows

import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import jakarta.sql.DataSource;
import io.quarkus.db.flyway.Flyway;

import static java.util.logging.Level.INFO;

@Singleton
@Startup
@TransactionManagement(TransactionManagementType.BEAN)
public class DataBaseMigrationStartup {

    @Inject
    private Logger logger;

    @Inject
    private DataSource dataSource;

    @PostConstruct
    public void startup() {


        logger.info("Initializing/migrating the database using Flyway");
        Flyway flyway = new io.quarkus.db.flyway.Flyway();
        flyway.setDataSource(dataSource);
        flyway.baseline();
        // Start the db.migration
        flyway.migrate();
    }

}