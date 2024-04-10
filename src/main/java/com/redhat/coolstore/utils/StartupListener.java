// Add the following at the top of the file
import org.jboss.logging.Logger;

// Change the import statement on line 6
import jakarta.inject.Inject;

// Update the class name to use the Quarkus naming convention
@QuarkusApplicationListener(name = "com.redhat.coolstore.utils.StartupListener")
public class StartupListener implements ApplicationLifecycleListener {

    @Inject
    private Logger log;

    @Override
    public void postStart(ApplicationLifecycleEvent evt) {
        log.info("AppListener(postStart)");
    }

    @Override
    public void preStop(ApplicationLifecycleEvent evt) {
        log.info("AppListener(preStop)");
    }
}
