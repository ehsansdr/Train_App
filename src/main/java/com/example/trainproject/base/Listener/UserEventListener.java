package com.example.trainproject.base.Listener;


import com.example.trainproject.base.Event.UserEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {

    // this class will listen to the event and execute the message in console

    // if you want  to listen to the object that extend the ApplicationEvent give that i the parameter
    // Notice how our custom listener is parametrized with the generic type of custom event
    @EventListener
    public void handleUserEvent(UserEvent userEvent){
        System.out.println("this message is from UserEventListener without calling from any where : "
                + userEvent.getMessage());
    }

}
