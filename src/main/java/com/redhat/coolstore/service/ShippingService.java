@Path("/shipping")
@ApplicationScoped
public class ShippingService {

    @GET
    @Path("/calculate")
    public double calculateShipping(@QueryParam("cartItemTotal") Double cartItemTotal) {
        // Implement shipping calculation logic here
    }

    @GET
    @Path("/calculateInsurance")
    public double calculateShippingInsurance(@QueryParam("cartItemTotal") Double cartItemTotal) {
        // Implement shipping insurance calculation logic here
    }
}
