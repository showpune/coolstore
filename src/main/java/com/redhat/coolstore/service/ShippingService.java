// Update the ShippingService class to use jakarta.ws.rs and java.math packages
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.service.ShippingInsuranceService;

@Path("/shipping")
@ApplicationScoped
public class ShippingService {

    private ShippingInsuranceService shippingInsuranceService;

    public ShippingService() {
        this.shippingInsuranceService = new ShippingInsuranceService();
    }

    @GET
    @Path("/calculate")
    public double calculateShipping(@QueryParam("amount") Double amount, @QueryParam("insurance") Double insurance) {
        // ... existing code ...
    }

    @GET
    @Path("/calculateInsurance")
    public double calculateShippingInsurance(@QueryParam("amount") Double amount) {
        if (amount != null && amount >= 25 && amount < 100) {
            return shippingInsuranceService.getPercentOfTotal(amount, 0.02);
        } else if (amount != null && amount >= 100 && amount < 500) {
            return shippingInsuranceService.getPercentOfTotal(amount, 0.015);
        } else if (amount != null && amount >= 500 && amount < 10000) {
            return shippingInsuranceService.getPercentOfTotal(amount, 0.01);
        }

        return 0;
    }
}

// Create the ShippingInsuranceService class as a Quarkus CDI bean
import jakarta.enterprise.context.RequestScoped;
import java.math.BigDecimal;
import java.util.function.Function;

import com.redhat.coolstore.model.ShoppingCart;

@RequestScoped
public class ShippingInsuranceService {

    @Override
    public Double getPercentOfTotal(Double value, Double percentOfTotal) {
        return BigDecimal.valueOf(value * percentOfTotal)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public double calculateShippingInsurance(Double amount) {
        // Implement shipping insurance calculation here
        return 0;
    }
}