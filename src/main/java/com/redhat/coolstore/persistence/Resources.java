// Add @Path and @GET annotations to inject the ExtendedContext bean into the UserResource class
@Path("/users")
@Produces
public class UserResource {

    @Inject
    private ExtendedContext extendedContext;

    @GET
    public Response getUsers() {
        // ...
    }
}