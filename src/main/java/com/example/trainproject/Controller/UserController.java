package com.example.trainproject.Controller;

import com.example.trainproject.Event.UserEvent;
import com.example.trainproject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-controller")
public class UserController {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/hello-user")
    public String helloUser(){
        UserEvent userEvent = new UserEvent(this,"event call");
        eventPublisher.publishEvent(userEvent);

        return "Event triggered with message: event call";
    }

}
