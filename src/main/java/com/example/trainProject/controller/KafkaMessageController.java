package com.example.trainProject.controller;

import com.example.trainproject.service.KafkaProducerService;
import org.apache.kafka.common.protocol.types.Field.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafkaProducer")
public class KafkaMessageController {

  @Autowired
  private KafkaProducerService kafkaProducerService;


  @PostMapping("/produce-message")
  public String sendMessage(@RequestBody String message) {
    kafkaProducerService.sendMessage(message);
    return "Message sent: " + message;
  }

}
