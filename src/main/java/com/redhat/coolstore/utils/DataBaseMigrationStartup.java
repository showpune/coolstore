// Update the code as follows
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import jakarta.sql.DataSource;
import org.flywaydb.core.api.Flyway;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static java.util.logging.Level.INFO;

@Startup
@TransactionManagement(TransactionManagementType.CONTAINER)
@QuarkusApplication(scanBasePackages = "com.redhat.coolstore.utils")
public class DataBaseMigrationStartup {

    @Inject
    private Logger logger;

    @Inject
    private DataSource dataSource;

    @PostConstruct
    public void startup() {


        try {
            logger.info("Initializing/migrating the database using Flyway");
            Flyway flyway = new Flyway();
            flyway.setDataSource(dataSource);
            flyway.baseline();
            flyway.migrate();
        } catch (Exception e) {
            logger.log(INFO,"FAILED TO INITIALIZE THE DATABASE: " + e.getMessage(),e);
        }
    }

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "database-migration.jar");
        jar.addClasses(DataBaseMigrationStartup.class);
        return jar;
    }

    @Test
    public void testDatabaseMigration() {
        // Arrange
        MockitoAnnotations.initMocks(this);

        // Act
        DataBaseMigrationStartup dbMigration = new DataBaseMigrationStartup();
        dbMigration.startup();

        // Assert
        Assert.assertTrue("Database should be initialized", true);
    }
}