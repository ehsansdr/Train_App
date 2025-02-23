package com.example.trainproject.Config;

import com.example.trainproject.Job.TimeScheduledJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class JobConfig {

    @Bean
    public JobDetail timeScheduledJobDetail() {
        return JobBuilder.newJob(TimeScheduledJob.class)
                .withIdentity("TimeScheduledJob")
                .storeDurably()  // This ensures the job is stored even if it's not triggered
                .build();
    }

    @Bean
    public Trigger timeScheduledJobTrigger(JobDetail timeScheduledJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(timeScheduledJobDetail)  // Using the JobDetail bean here
                .startAt(new Date(System.currentTimeMillis() + (10 * 1000))) // Trigger job 10 seconds after the application starts
                .build();
    }

//    @Bean
//    public Trigger timeScheduledJobTrigger(JobDetail timeScheduledJobDetail) {
//        return TriggerBuilder.newTrigger()
//                .forJob(timeScheduledJobDetail)
//                .withIdentity("TimeScheduledJobTrigger")
//                .startAt(new Date(System.currentTimeMillis() + 10 * 1000)) // Start after 10 seconds
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                        .withIntervalInSeconds(10) // Run every 10 seconds
//                        .repeatForever())
//                .build();
//    }
}
