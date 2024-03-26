// Update the StartupListener class to use jakarta.inject and Quarkus classes
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.wildfly.extension.io.FileSystem;
import org.wildfly.extension.io.Ini;
import org.wildfly.extension.undertow.Undertow;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class StartupListener implements ApplicationLifecycleListener {

    @Inject
    @Named("fileSystem")
    private FileSystem fileSystem;

    @Inject
    @Named("config")
    private Ini config;

    @Inject
    private Logger log;

    @Override
    public void postStart(@Observes ApplicationLifecycleEvent evt) {
        log.info("AppListener(postStart)");
    }

    @Override
    public void preStop(@Observes ApplicationLifecycleEvent evt) {
        log.info("AppListener(preStop)");
    }
}