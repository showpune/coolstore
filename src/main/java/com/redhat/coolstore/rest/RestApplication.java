// Update the import statement in the `RestApplication.java` file
import io.quarkus.jaxrs.Path;
import io.quarkus.jaxrs.client.ClientConfig;
import io.quarkus.jwt.JwtConfig;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.keycloak.quarkus.KeycloakSecurityExtension;

// Update the `RestApplication` class to use the `@QuarkusApplication` annotation
@ApplicationPath("/services")
@QuarkusApplication(
    clientConfigurers = {
        io.quarkus.resteasy.client.ClientConfigurers.jaxrsClientConfigurer
    },
    security = {
        io.quarkus.security.Security.builder()
            .realms(realm -> realm.realmName("default").rolesAllowed("USER"))
            .build()
    },
    scanConfigurers = {
        io.quarkus.arc.scanning.ScanningConfigurers.register(KeycloakSecurityExtension.class)
    }
)
public class RestApplication extends Application {
    // Add the @RegisterRestService annotation to register the REST service
    @RegisterRestService
    public static class ExampleResource {
        // Add the @Incoming annotation to define the incoming request method
        @Incoming
        @Path("/example")
        public String example(@Context Request request) {
            return "Hello, " + request.getHeader("name");
        }
    }
}