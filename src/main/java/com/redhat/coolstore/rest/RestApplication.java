// Add the following at the top of the file
import io.quarkus.builder.BuildException;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.runtime.annotations.Recorder;
import org.jboss.resteasy.plugins.server.servlet.HttpServletRequestContextIntegration;
import org.jboss.resteasy.plugins.server.servlet.ResteasyDeployment;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@ApplicationPath("/services")
public class RestApplication {

    @Context
    private SecurityContext securityContext;

    // Remove the `addResteasyProvider` build step
    // Remove the `addServlets` and `addFilters` build steps
    // Register the SecurityContextProvider directly in the application class
}