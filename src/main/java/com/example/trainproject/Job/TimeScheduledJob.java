package com.example.trainproject.Job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@ConditionalOnExpression("'${Trian.enableJob}'=='true'")
public class TimeScheduledJob extends BaseScheduler implements Job {

    @Autowired
    public TimeScheduledJob() {
        super("TimeScheduled", 5);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // In Quartz, the execute method is the core of any job.
        // Job logic goes here
        log.info("TimeScheduledJob executing");
    }

    @Bean
    @Qualifier("timeScheduledJobDetail")
    public JobDetail timeScheduledJobDetail() {
        // Generate a unique job identity by appending current timestamp to the job name
        return JobBuilder.newJob(TimeScheduledJob.class)
                .withIdentity("TimeScheduledJob-" + System.currentTimeMillis()) // Unique identity using timestamp
                .storeDurably()  // This ensures the job is stored even if it's not triggered
                .build();
    }

    @Bean
    @Qualifier("timeScheduledJobTrigger")
    public Trigger timeScheduledJobTrigger(@Qualifier("timeScheduledJobDetail") JobDetail timeScheduledJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(timeScheduledJobDetail)  // Link the trigger to the job
                .startAt(new Date(System.currentTimeMillis() + (10 * 1000)))  // Start 10 seconds after application starts
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)  // Execute every 10 seconds
                        .repeatForever())  // Repeat indefinitely
                .withIdentity("TimeScheduledTrigger-" + System.currentTimeMillis() + "-" + Math.random())  // Unique trigger identity
                .build();
    }


    @Bean
    @Qualifier("timeScheduledJobTScheduler")
    public Scheduler timeScheduledJobTScheduler(@Qualifier("timeScheduledJobTrigger") Trigger trigger,
                                                @Qualifier("timeScheduledJobDetail") JobDetail job, SchedulerFactoryBean factory)
            throws SchedulerException {
        log.debug("Getting a handle to the Scheduler");
        Scheduler scheduler = factory.getScheduler();

        // Check if the job exists and delete it
        JobKey jobKey = job.getKey();
//        This code checks if the job already exists using scheduler.checkExists(jobKey).
//        If the job exists, it deletes the old job using scheduler.
//        deleteJob(jobKey) before scheduling the new one.
        if (scheduler.checkExists(jobKey)) {
            log.info("Job {} already exists, deleting the old job.", jobKey);
            scheduler.deleteJob(jobKey);
        }
        // be care fule this code handle the existence of job in the project if the job alredy exist you will get the exception

        // Now schedule the job again
        scheduler.scheduleJob(job, trigger);

        log.debug("Starting Scheduler threads");
        scheduler.start();
        return scheduler;
    }


}
