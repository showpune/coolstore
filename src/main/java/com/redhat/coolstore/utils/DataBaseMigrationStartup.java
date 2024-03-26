// Update the DataBaseMigrationStartup class to use jakarta.annotation and @FlywayConfiguration
@Quarkus(
    dependencies = {
        "org.flywaydb:flyway-core",
        "io.quarkus:quarkus-datasource-flyway"
    },
    mainClass = DataBaseMigrationStartup.class
)
public class DataBaseMigrationStartup {

    @Inject
    private Logger logger;

    @Resource(mappedName = "java:jboss/datasources/CoolstoreDS")
    @JakartaNamespaces({"javax.sql.DataSource", "jakarta.transaction.Transaction", "jakarta.validation.Validation"})
    private DataSource dataSource;

    @Inject
    @JakartaAnnotations.Inject
    @FlywayConfiguration
    private Flyway flyway;

    @PostConstruct
    public void startup() {
        logger.info("Initializing/migrating the database using FlyWay");
        flyway.setDataSource(dataSource);
        flyway.baseline();
        flyway.migrate();
    }
}