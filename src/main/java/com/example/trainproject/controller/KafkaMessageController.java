package com.example.trainproject.controller;

import com.example.trainproject.Model.KafkaProduceMessage;
import com.example.trainproject.service.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/kafkaProducer")
public class KafkaMessageController {

  @Autowired
  private KafkaProducerService kafkaProducerService;


  @PostMapping("/produce-message")
  public String sendMessage(@RequestBody KafkaProduceMessage message) {
    kafkaProducerService.sendMessageJson(message);
    log.info("message send successfully {}", message);
    return "Message sent: " + message;
  }

}
