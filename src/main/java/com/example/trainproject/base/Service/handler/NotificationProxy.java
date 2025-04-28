package com.example.trainproject.base.Service.handler;


import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
@NoArgsConstructor
public class NotificationProxy implements NotificationHandler {

  @Setter
  private NotificationHandler notificationHandler;

  @Override
  public void handle(String message) throws Exception {
    log.info("[Proxy] started");

    if (notificationHandler != null) {
      notificationHandler.handle(message);
    } else {
      log.info("no handler found");
    }

    log.info("[Proxy] ended");
  }
}
