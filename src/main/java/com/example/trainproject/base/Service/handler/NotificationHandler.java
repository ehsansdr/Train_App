package com.example.trainproject.base.Service.handler;

import org.springframework.cloud.openfeign.FeignClient;

@FunctionalInterface
public interface NotificationHandler {

  void handle(String message) throws Exception;

}
