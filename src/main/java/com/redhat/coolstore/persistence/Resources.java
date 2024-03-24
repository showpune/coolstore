@QuarkusApplication(
    mainClass = CoolstoreApplication.class,
    services = {
        // Add any required services here
    }
)
@ResteasyBindings(basePackages = "com.redhat.coolstore.persistence")
public class Resources {

    @Inject
    @ExtendedContext
    private ExtendedContext extendedContext;

    @GET
    @Path("/extendedContext")
    @Produces(ExtendedContext.class)
    public ExtendedContext getExtendedContext() {
        return extendedContext;
    }
}