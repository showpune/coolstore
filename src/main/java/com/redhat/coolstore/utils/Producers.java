// Update the Java file to use Jakarta Enterprise and Quarkus annotations
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Named;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

@ApplicationScoped
public class Producers {

    @Named
    private static final Logger logger = Logger.getLogger(Producers.class.getName());

    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        return getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}