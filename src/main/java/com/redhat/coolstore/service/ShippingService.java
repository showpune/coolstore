@Path("/shipping")
@Stateless
public class ShippingService implements ShippingServiceRemote {

    @Override
    @Path("/calculate")
    @GET
    public Response calculateShipping(@QueryParam("amount") Double amount) {
        // Implement shipping calculation logic here
        return Response.ok().build();
    }

    @Override
    @Path("/calculateInsurance")
    @GET
    public Response calculateShippingInsurance(@QueryParam("amount") Double amount) {
        // Implement shipping insurance calculation logic here
        return Response.ok().build();
    }
}