// Update the import statements
import io.quarkus.builder.BuildException;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.hibernate.orm.deployment.HibernateOrmFeature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Past;
import quarkus.arc.processor.FieldProcessor;
import quarkus.arc.processor.FieldProcessorBuilder;
import quarkus.arc.processor.MethodProcessor;
import quarkus.arc.processor.MethodProcessorBuilder;
import quarkus.hibernate.orm.runtime.spi.FieldHandler;
import quarkus.runtime.annotations.ConfigPhase;
import quarkus.runtime.annotations.RecordStage;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.quarkus.arc.processor.FieldProcessor.processFields;
import static io.quarkus.arc.processor.MethodProcessor.processMethods;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;
import static lombok.Builder.builder;
import static lombok.Data.class;
import static lombok.NoArgsConstructor.class;
import static lombok.ToString.simpleName;
import static org.hibernate.validator.constraints.NotBlank.notBlank;
import static org.hibernate.validator.constraints.NotEmpty.notEmpty;
import static quarkus.arc.processor.FieldProcessorBuilder.builder;
import static quarkus.arc.processor.MethodProcessorBuilder.builder;

// Update the ShippingService class
@Path("/shipping")
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class ShippingService {

    @NonNull
    @Min(MIN_VALUE)
    @Max(MAX_VALUE)
    @Past
    @NotEmpty
    @NotBlank
    private BigDecimal shippingRate;

    @NonNull
    @NotEmpty
    @Past
    @NotBlank
    private List<Double> shippingInsuranceRates;

    @Inject
    private ShippingService() {
    }

    @BuildStep
    public static ShippingService build(FieldProcessorBuilder processorBuilder) {
        return new ShippingService();
    }

    @Inject
    private ShippingService(FieldHandler fieldHandler) {
        this.shippingRate = fieldHandler.get(ShippingService.class, "shippingRate");
        this.shippingInsuranceRates = fieldHandler.get(ShippingService.class, "shippingInsuranceRates");
    }

    @GET
    @Path("/calculate")
    public BigDecimal calculateShipping(@NonNull @QueryParam("cartItemTotal") BigDecimal cartItemTotal) {
        if (cartItemTotal == null) {
            return BigDecimal.ZERO;
        }

        double shippingCost = shippingRate.doubleValue() * cartItemTotal.doubleValue();
        return BigDecimal.valueOf(shippingCost);
    }

    @GET
    @Path("/calculateInsurance")
    public BigDecimal calculateShippingInsurance(@NonNull @QueryParam("cartItemTotal") BigDecimal cartItemTotal) {
        if (cartItemTotal == null) {
            return BigDecimal.ZERO;
        }

        double shippingInsuranceCost = shippingInsuranceRates.stream()
                .mapToDouble(rate -> rate * cartItemTotal.doubleValue())
                .sum()
                .orElse(0);

        return BigDecimal.valueOf(shippingInsuranceCost);
    }
}