// Update the ShippingService class to use jakarta.ws.rs and java.math packages
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.service.ShippingInsuranceService;

@Path("/shipping")
public class ShippingService {

    private ShippingInsuranceService shippingInsuranceService;

    public ShippingService(@Inject @Named("shippingInsuranceService") ShippingInsuranceService shippingInsuranceService) {
        this.shippingInsuranceService = shippingInsuranceService;
    }

    @GET
    @Path("/calculate")
    public Double calculateShipping(@QueryParam("amount") BigDecimal amount, @QueryParam("insurance") Double insurance) {
        // ... existing code ...
    }

    @GET
    @Path("/calculateInsurance")
    public Double calculateShippingInsurance(@QueryParam("amount") BigDecimal amount) {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
            if (amount.compareTo(BigDecimal.valueOf(25)) >= 0 && amount.compareTo(BigDecimal.valueOf(100)) < 0) {
                return shippingInsuranceService.calculateShippingInsurance(amount, 0.02);
            } else if (amount.compareTo(BigDecimal.valueOf(100)) >= 0 && amount.compareTo(BigDecimal.valueOf(500)) < 0) {
                return shippingInsuranceService.calculateShippingInsurance(amount, 0.015);
            } else if (amount.compareTo(BigDecimal.valueOf(500)) >= 0 && amount.compareTo(BigDecimal.valueOf(10000)) < 0) {
                return shippingInsuranceService.calculateShippingInsurance(amount, 0.01);
            }
        }

        return 0;
    }
}

// Create the ShippingInsuranceService class
import jakarta.ejb.Stateless;
import java.math.BigDecimal;
import java.util.function.Function;

import com.redhat.coolstore.model.ShoppingCart;

@Stateless
public class ShippingInsuranceService {

    @Inject
    private Function<Double, Double> getPercentOfTotal;

    public Double calculateShippingInsurance(ShoppingCart sc) {
        // Implement shipping insurance calculation here
        return getPercentOfTotal.apply(sc.getTotalAmount(), 0.02);
    }
}