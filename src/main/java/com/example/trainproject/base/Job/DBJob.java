package com.example.trainproject.base.Job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DBJob extends BaseScheduler implements Job {

  public DBJob() {
    super("DBJob", 1);
  }

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    System.out.println("DBJob execute");
  }

  @Bean
  @Qualifier("dBJobDetail")
  public JobDetail dBJobDetail() {
    return JobBuilder.newJob(QuartsJob.class)
        .withIdentity("dBJob", "group3") // Ensure you are using a unique name and group
        .storeDurably()  // Keeps the job even if it isn't triggered
        .build();
  }

  @Bean
  @Qualifier("dBJobTrigger")
  public Trigger dBJobTrigger() {
    return TriggerBuilder.newTrigger()
        .forJob("dBJob", "group3")
        .withIdentity("dBTrigger", "group3")
        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
            .withIntervalInSeconds(10)
            .repeatForever())
        .build();
  }

  @Bean
  @Qualifier("dBJobScheduler")
  public Scheduler dBJobScheduler(
      @Qualifier("dBJobTrigger") Trigger trigger,
      @Qualifier("dBJobDetail") JobDetail job,
      SchedulerFactoryBean factory
  ) {
    log.debug("JOB: Getting a handle to the dBJobScheduler");
    Scheduler scheduler = factory.getScheduler();

    return scheduler;
  }
}
