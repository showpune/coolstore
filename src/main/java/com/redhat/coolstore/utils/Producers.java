// Update the Producers class to use Jakarta Enterprise and Logging in Quarkus
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.logging.Logger;
import java.util.stream.Stream;

@ApplicationScoped
public class Producers {

    @Named
    private static final Logger log = LoggerFactory.getLogger(Producers.class);

    @Produces
    public Job produceJob(JobDataMap jobDataMap, Scheduler scheduler, Trigger trigger) {
        return new MyJob(jobDataMap, scheduler, trigger);
    }

    private static class MyJob implements Job {
        private final JobDataMap jobDataMap;
        private final Scheduler scheduler;
        private final Trigger trigger;

        public MyJob(JobDataMap jobDataMap, Scheduler scheduler, Trigger trigger) {
            this.jobDataMap = jobDataMap;
            this.scheduler = scheduler;
            this.trigger = trigger;
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            log.info("Running job with data: {}", jobDataMap.get("data"));
            // Add your job code here
        }
    }
}