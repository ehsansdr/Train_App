package com.example.trainproject.base.Controller;

import com.example.trainproject.base.Model.KafkaProduceMessage;
import com.example.trainproject.base.Service.KafkaProducerService;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
  @RolesAllowed("ADMIN")
  public String sendMessage(@RequestBody KafkaProduceMessage message) {
    kafkaProducerService.sendMessage(message);
    log.info("message send successfully {}", message);
    return "Message sent: " + message;
  }

}