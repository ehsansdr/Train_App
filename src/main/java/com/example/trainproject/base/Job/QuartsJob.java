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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnExpression("'${Train.enableJob}' == 'true' ")
public class QuartsJob extends BaseScheduler implements Job {

  public QuartsJob() {
    super("QuartsJob", 1);
  }

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    // Task to execute
    System.out.println("QuartsJob executed");
  }

  // Define job detail
  @Bean
  @Qualifier("quartsJobDetail")
  public JobDetail quartsJobDetail() {
    return JobBuilder.newJob(QuartsJob.class)
        .withIdentity("quartsJob", "group1") // Ensure you are using a unique name and group
        .storeDurably()  // Keeps the job even if it isn't triggered
        .build();
  }

  // Define trigger for the job
  @Bean
  @Qualifier("quartsJobTrigger")
  public Trigger quartsJobTrigger() {
    return TriggerBuilder.newTrigger()
        .forJob("quartsJob", "group1")  // Reference job by identity and group
        .withIdentity("quartsTrigger", "group1")
        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
            .withIntervalInSeconds(10)  // Interval in seconds
            .repeatForever())  // Repeat forever
        .build();
  }

  // Scheduler configuration
  @Bean
  @Qualifier("quartsJobScheduler")
  public Scheduler quartsJobScheduler(
      @Qualifier("quartsJobTrigger") Trigger trigger,
      @Qualifier("quartsJobDetail") JobDetail job,
      SchedulerFactoryBean factory) throws SchedulerException {

//    // Check if the job already exists before trying to schedule
//    if (scheduler.checkExists(job.getKey())) {
//      log.warn("Job with ID {} already exists, skipping scheduling", job.getKey());
//    } else {
//      // Schedule the job only if it doesn't exist
//      scheduler.scheduleJob(job, trigger);
//      log.debug("JOB: Starting Scheduler threads");
//      scheduler.start();
//    }

    return factory.getScheduler();
  }
}
