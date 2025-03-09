package com.example.trainproject.base.Job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@RequiredArgsConstructor
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
