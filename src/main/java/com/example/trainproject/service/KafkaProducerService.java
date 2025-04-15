package com.example.trainproject.service;

import com.example.trainproject.Model.KafkaProduceMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class KafkaProducerService {

  @Value(value="${my.kafka.topic}")
  private String kafkaTopic;

  @Autowired
  private KafkaTemplate<String, Object> kafkaTemplate;
  @Autowired
  private ObjectMapper objectMapper;


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


  public void sendMessageJson(KafkaProduceMessage message) {
    try {
      kafkaTemplate.send(kafkaTopic, message)
          .whenComplete((result, ex) -> {
            if (ex != null) {
              log.error("❌ Failed to send message: {}", ex.getMessage(), ex);
            } else {
              log.info("✅ Message sent successfully to topic: {}, partition: {}, offset: {}",
                  kafkaTopic,
                  result.getRecordMetadata().partition(),
                  result.getRecordMetadata().offset());
            }
          });

    } catch (Exception e) {
      log.error("❌ Failed to serialize message to JSON: {}", e.getMessage(), e);
    }
  }

  public <T> void sendMessage(T message) {
    kafkaTemplate.send(kafkaTopic, message)
        .whenComplete((result, ex) -> {
          if (ex != null) {
            log.error("❌ Failed to send message: {}", ex.getMessage(), ex);
          } else {
            log.info("✅ Message sent successfully to topic: {}, partition: {}, offset: {}",
                kafkaTopic,
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().offset());
          }
        });
  }

}
