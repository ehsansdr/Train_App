package com.example.trainproject.base.Config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {



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
