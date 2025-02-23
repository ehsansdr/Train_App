package com.example.trainproject.Job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
public abstract class BaseScheduler {
    protected String jobTitlePrefix;
    @Getter
    protected Integer jobIntervalInMinutes;

    public String getDetailTitle(){
        return jobTitlePrefix + "Details";
    }

    public String getDetailDescription(){
        return jobTitlePrefix + " description";
    }

    public String getTriggerTitle(){
        return jobTitlePrefix + "Trigger";
    }

    public String getTriggerDescription(){
        return jobTitlePrefix + " description";
    }

}
