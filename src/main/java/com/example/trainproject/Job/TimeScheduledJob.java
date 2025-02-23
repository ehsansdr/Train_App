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
@ConditionalOnExpression("'${Trian.enableJob}'=='true'") //  means that the job will only be created if the Trian.enableJob property in your application’s properties or YAML file is set to true.
public class TimeScheduledJob extends BaseScheduler implements Job {

    //@Autowired // if you have @Autowired, the name of the method will go red. have this red line do not stop the program
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

        // JobDetail: This represents the job configuration in Quartz. It contains information about the job,
        // such as the job class and identity.
        return JobBuilder.newJob(TimeScheduledJob.class)
                //withIdentity(): This sets the unique identity of the job. In this case,
                // it appends the current system time (System.currentTimeMillis()) to ensure that the job name is unique.
                .withIdentity("TimeScheduledJob-" + System.currentTimeMillis()) // Unique identity using timestamp
                //storeDurably(): This ensures that the job is stored even if it is not triggered immediately.
                // This is useful when you need to store jobs that may not always be running but are ready to be triggered later.
                .storeDurably()  // This ensures the job is stored even if it's not triggered
                .build();
    }

    @Bean
    @Qualifier("timeScheduledJobTrigger")
    public Trigger timeScheduledJobTrigger(@Qualifier("timeScheduledJobDetail") JobDetail timeScheduledJobDetail) {

//        Trigger: A trigger in Quartz is used to define when and how often a job will be executed.

        return TriggerBuilder.newTrigger()
                // forJob(): Links the trigger to a specific job (in this case, timeScheduledJobDetail).
                .forJob(timeScheduledJobDetail)  // Link the trigger to the job with bean injection
                // startAt(): Specifies the time when the trigger should first fire.
                // Here, we’re setting it to start 10 seconds after the application starts.
                .startAt(new Date(System.currentTimeMillis() + (10 * 1000)))  // Start 10 seconds after application starts
                // withSchedule(): Defines the scheduling rule.
                // SimpleScheduleBuilder.simpleSchedule() creates a simple trigger that repeats indefinitely every 10 seconds.
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)  // Execute every 10 seconds
                        .repeatForever())  // Repeat indefinitely
                // withIdentity(): Sets a unique identifier for the trigger,
                // just like for the job, ensuring no conflicts.
                .withIdentity("TimeScheduledTrigger-" + System.currentTimeMillis() + "-" + Math.random())  // Unique trigger identity
                .build();
    }

    @Bean
    @Qualifier("timeScheduledJobTScheduler")
    public Scheduler timeScheduledJobTScheduler(@Qualifier("timeScheduledJobTrigger") Trigger trigger,
                                                @Qualifier("timeScheduledJobDetail") JobDetail job, SchedulerFactoryBean factory)
            throws SchedulerException {
        log.debug("Getting a handle to the Scheduler");
        // Scheduler: The Scheduler is responsible for managing jobs and triggers.
        // It schedules, executes, and manages the lifecycle of jobs.
        Scheduler scheduler = factory.getScheduler();

        // Check if the job exists and delete it
        // This code checks if the job already exists using scheduler.checkExists(jobKey).
        // If the job exists, it deletes the old job using scheduler.
        // deleteJob(jobKey) before scheduling the new one.
        JobKey jobKey = job.getKey();
        if (scheduler.checkExists(jobKey)) { // checkExists(jobKey): This checks if a job with the given key already exists. If it does, the old job is deleted with deleteJob(jobKey).
            log.info("Job {} already exists, deleting the old job.", jobKey);
            scheduler.deleteJob(jobKey);
        }
        // be care ful this code handle the existence of job in the project if the job already exist you will get the exception

        // Now schedule the job again
        // scheduleJob(job, trigger): Schedules the job with the specified trigger.
        // This will cause the job to run based on the defined trigger configuration.
        scheduler.scheduleJob(job, trigger);

        log.debug("Starting Scheduler threads");
        // scheduler.start(): Starts the scheduler. Once this is called, the job will begin running according to its trigger.
        scheduler.start();
        return scheduler;
    }


}
