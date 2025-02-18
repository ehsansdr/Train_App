package com.example.trainproject.Event;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.awt.event.ActionEvent;

@Slf4j
public class UserEvent extends ApplicationEvent {

    private String message;

    public UserEvent(Object source,String message){
        super(source);
        this.message = message;
    }

    // can create getter and setter with lombok because we extend ApplicationEvent
    // and we get error : lombok needs simple constructor in base class
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
