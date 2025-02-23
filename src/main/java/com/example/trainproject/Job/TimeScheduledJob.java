package com.example.trainproject.Job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@ConditionalOnExpression("'${Trian.enableJob}'=='true'")
public class TimeScheduledJob extends BaseScheduler implements Job {


    private final JobDetail timeScheduledJobDetail;

    @Autowired
    public TimeScheduledJob(JobDetail timeScheduledJobDetail) {
        super("TimeScheduledJob", 5);
        this.timeScheduledJobDetail = timeScheduledJobDetail;
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // In Quartz, the execute method is the core of any job.
        // Job logic goes here
        log.info("TimeScheduledJob executing");
    }

}
