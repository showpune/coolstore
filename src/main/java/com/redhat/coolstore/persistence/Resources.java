// Updated File
@Path("/users")
public class UserResource {

    @Inject
    @Path("/{id}")
    User user;

    @Inject
    ExtendedContext context;

    @GET
    public Response getUser(@PathParam("id") String id) {
        return Response.ok(user).build();
    }
}