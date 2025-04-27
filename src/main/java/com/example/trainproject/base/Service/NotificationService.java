package com.example.trainproject.base.Service;

import com.example.trainproject.base.Service.handler.NotificationHandler;

public class NotificationService implements NotificationHandler {

  @Override
  public void handle(String message) throws Exception {
    System.out.println("real handling message : " + message);
  }
}
