package com.example.trainproject.base.Job;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnExpression("'${Train.enableJob}' == 'true'")
public class SelfJob extends BaseScheduler implements Job {
  // be careful about import

  public SelfJob() {
    super("SelfJob", 1);
  }

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    // The task that will be executed
    System.out.println("SelfJob executing");
  }

  // Job detail definition
  @Bean
  @Qualifier("selfJobDetail")
  public JobDetail selfJobDetail() {
    return JobBuilder.newJob(SelfJob.class)
        .withIdentity("SelfJob", "group2")  // Unique name and group for the job
        .storeDurably()  // Keep the job even if not triggered
        .build();
  }

  // Trigger definition for the job
  @Bean
  @Qualifier("selfJobTrigger")
  public Trigger selfJobTrigger() {
    return TriggerBuilder.newTrigger()
        .forJob("SelfJob", "group2")  // Reference the correct job "SelfJob" in "group2"
        .withIdentity("SelfJobTrigger", "group2")  // Unique name for the trigger
        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
            .withIntervalInSeconds(5)  // Execute every 5 seconds
            .repeatForever()  // Repeat indefinitely
        )
        .build();
  }

  @Bean
  @Qualifier("selfJobScheduler")
  public Scheduler selfJobScheduler(
      @Qualifier("selfJobTrigger") Trigger trigger,
      @Qualifier("selfJobDetail") JobDetail job,
      SchedulerFactoryBean factory
  ) throws SchedulerException {
    log.debug("JOB: Getting a handle to the SelfJobScheduler");
    Scheduler scheduler = factory.getScheduler();

//    // Check if the job already exists
//    if (scheduler.checkExists(job.getKey())) {
//      log.warn("Job with ID {} already exists, skipping scheduling", job.getKey());
//    } else {
//      // Schedule the job only if it doesn't exist
//      scheduler.scheduleJob(job, trigger);
//      log.debug("JOB: Job scheduled successfully");
//    }

    log.debug("JOB: Starting Scheduler threads");
    scheduler.start();

    return scheduler;
  }

}
