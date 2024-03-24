import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jakarta.enterprise.inject.spi.InjectionPoint.get;

@Dependent
public class Producers {

    @Inject
    public Producers() {
    }

    @Produces
    @Any
    public Logger produceLog() {
        return LoggerFactory.getLogger(getCurrentClass().getName());
    }

    @Inject
    public void configureLogging(@Named("logger") Logger logger) {
        // Add custom logging configuration here
    }
}