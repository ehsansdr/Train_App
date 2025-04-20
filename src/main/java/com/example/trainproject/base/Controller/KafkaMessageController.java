package com.example.trainproject.base.Controller;

import com.example.trainproject.base.Model.Card;
import com.example.trainproject.base.Model.KafkaProduceMessage;
import com.example.trainproject.base.Service.KafkaProducerService;
import com.example.trainproject.base.Util.Wapper.TransferWrapper;
import com.example.trainproject.base.Util.Wapper.TransferWrapperUtil;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
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

  // @KafkaListener(topics = "${my.kafka.topic}", groupId = "group-id")
  public void consume(String messageJson) throws Exception {
    // Deserialize message
    TransferWrapper<?> wrapper = TransferWrapperUtil.fromJsonSafely(messageJson);

    // Handle the deserialized wrapper
    Card card = (Card) wrapper.getData();
    System.out.println("Received card: " + card);

  }

}