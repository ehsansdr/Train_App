package com.example.trainproject.controller;

import com.example.trainproject.constant.Channel;
import com.example.trainproject.constant.NotificationTopic;
import com.example.trainproject.dto.NotificationDto;
import com.example.trainproject.service.KafkaProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;
import ir.barook.notification.proto.NotificationKafkaDto;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

  @Autowired
  private KafkaTemplate<String, byte[]> kafkaTemplate;

  @Value("${kafka-info.topics.notification}")
  String kafkaTopic;


  @PostMapping("/produce-message")
  public NotificationDto sendMessage() {
    Faker faker = new Faker();

    NotificationDto notificationDto = new NotificationDto();

    notificationDto.setReceiverId(UUID.randomUUID());
    notificationDto.setNotificationTopic(NotificationTopic.STEP_PROCESSING);
    notificationDto.setContactInfo("09128884557");
    notificationDto.setChannel(Channel.SMS);
//    notificationDto.setMessageBody(faker.lorem().paragraph());
    notificationDto.setDeliveryDate(ZonedDateTime.now());
    notificationDto.setExpiryDate(ZonedDateTime.now().plusDays(1));

    try {
      NotificationKafkaDto protoMsg = toProto(notificationDto);

      kafkaTemplate.send(kafkaTopic, protoMsg.toByteArray());

      log.info("Sending protobuf kafka message to topic {}: {}", kafkaTopic, protoMsg);
    } catch (Exception e) {
      throw new RuntimeException("Failed to send notification", e);
    }
    return notificationDto;
  }


  @PostMapping("/generate-kafka-message")
  public NotificationDto sendMessage(@RequestBody NotificationDto notificationDto) {
    try {
      NotificationKafkaDto protoMsg = toProto(notificationDto);
      kafkaTemplate.send(kafkaTopic, protoMsg.toByteArray());
      log.info("Sent protobuf Kafka message to topic {}: {}", kafkaTopic, protoMsg);
    } catch (Exception e) {
      log.error("Failed to send notification", e);
      throw new RuntimeException("Failed to send notification", e);
    }
    return notificationDto;
  }


  private NotificationKafkaDto toProto(NotificationDto dto) {
    NotificationKafkaDto.Builder builder = NotificationKafkaDto.newBuilder();

    if (dto.getReceiverId() != null) {
      builder.setReceiverId(dto.getReceiverId().toString());
    }
    if (dto.getContactInfo() != null) {
      builder.setContactInfo(dto.getContactInfo());
    }
    if (dto.getChannel() != null) {
      builder.setChannelValue(dto.getChannel().ordinal()); // assuming ordinal matches protobuf enum values
    }
    if (dto.getNotificationTopic() != null) {
      builder.setNotificationTopicValue(dto.getNotificationTopic().ordinal());
    }
//    if (dto.getMessageBody() != null) {
//      builder.setMessageBody(dto.getMessageBody());
//    }
    if (dto.getDeliveryDate() != null) {
      builder.setDeliveryDate(dto.getDeliveryDate().toString());
    }
    if (dto.getExpiryDate() != null) {
      builder.setExpiryDate(dto.getExpiryDate().toString());
    }

    if (dto.getTemplateVariables()  != null) {
      builder.putAllTemplateVariables(dto.getTemplateVariables());
    }

    if (dto.getTemplateVersion() != null) {
      builder.setTemplateVersion(dto.getTemplateVersion());
    }

    if (dto.getLanguage() != null) {
      builder.setLanguageValue(dto.getLanguage().ordinal());
    }

    return builder.build();  // this returns NotificationKafkaDto now matching method return type
  }



}
