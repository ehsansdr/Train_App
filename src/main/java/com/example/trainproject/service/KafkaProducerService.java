package com.example.trainproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class KafkaProducerService {

  private String kafkaTopic = "kafka_training_Es";

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  public void sendMessage(String message) {
    kafkaTemplate.send(kafkaTopic, message)
        .whenComplete((result, ex) -> {
          if (ex != null) {
            log.error("❌ Failed to send message: " + ex.getMessage());
          } else {
            log.info("✅ Message sent successfully to topic: " + kafkaTopic +
                ", partition: " + result.getRecordMetadata().partition() +
                ", offset: " + result.getRecordMetadata().offset());
          }
        });
  }



}
