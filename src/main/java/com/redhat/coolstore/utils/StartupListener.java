// Update the StartupListener class to use jakarta.inject and Quarkus logging
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.GroupMatcher;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
public class StartupListener {

    @Inject
    private Logger log;

    @Inject
    private Scheduler scheduler;

    @Inject
    private Job job;

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupListener.class);

    @Override
    public void preStart() {
        log.info("AppListener(preStart)");

        // Add code to start the Quartz job here

        // Add code to start other services here

    }

    @Override
    public void postStart(@ApplicationScoped
                          @Order(Ordered.HIGHEST_PRECEDENCE)
                          final JobDataMap jobDataMap) {
        log.info("AppListener(postStart)");

        // Add code to do something when the job is triggered here

        // Add code to start other components here

    }

    @Override
    public void preStop() {
        log.info("AppListener(preStop)");

        // Add code to stop the Quartz job here

        // Add code to stop other services here

    }

    @Inject
    public StartupListener(Scheduler scheduler, Job job) {
        this.scheduler = scheduler;
        this.job = job;
    }

}