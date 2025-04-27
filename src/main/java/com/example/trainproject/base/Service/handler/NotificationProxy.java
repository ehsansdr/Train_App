package com.example.trainproject.base.Service.handler;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
