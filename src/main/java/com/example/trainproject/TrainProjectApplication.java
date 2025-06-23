package com.example.trainproject;

import com.example.trainproject.Model.KafkaProduceMessage;
import com.example.trainproject.constant.Channel;
import com.example.trainproject.constant.NotificationTopic;
import com.example.trainproject.dto.NotificationKafkaDto;
import com.example.trainproject.service.KafkaProducerService;
import com.example.trainproject.util.KafkaUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class TrainProjectApplication {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  private KafkaUtil kafkaUtils;

  @Value("${kafka-info.topics.notification}")
  String kafkaTopic;


  public static void main(String[] args) {
    SpringApplication.run(TrainProjectApplication.class, args);

  }

  @Bean
  public CommandLineRunner monitorExistingTopic() {
    return args -> {
      kafkaUtils.monitorExistingTopic();
    };
  }

  // @Bean
  public CommandLineRunner topicGenerator() {
    return args -> {
      kafkaUtils.topicsCreator("from-kafka-in-train-app", 1, 3);
    };
  }

  // @Bean
  public CommandLineRunner init(KafkaProducerService kafkaProducerService) {
    return args -> {
      Faker faker = new Faker();
      KafkaProduceMessage kafkaProducedMessage = new KafkaProduceMessage();
      kafkaProducedMessage.setId(1);
      kafkaProducedMessage.setName(faker.name().name());
      kafkaProducedMessage.setDescription(faker.lorem().paragraph());
      kafkaProducerService.sendMessageJson(kafkaProducedMessage);
      System.out.println(kafkaProducedMessage);
    };
  }

  // @Bean
  public CommandLineRunner init() {
    return args -> {
      for (int i = 0; i < 10; i++) {
        NotificationKafkaDto notificationKafkaDto = new NotificationKafkaDto();

        notificationKafkaDto.setReceiverId(UUID.randomUUID());
        notificationKafkaDto.setNotificationTopic(NotificationTopic.STEP_PROCESSING);
        notificationKafkaDto.setSendAt(ZonedDateTime.now());
        notificationKafkaDto.setChannel(Channel.EMAIL);
        notificationKafkaDto.setMessageBody("hi do you see me");
        notificationKafkaDto.setContactInfo("what ever");
        notificationKafkaDto.setSendAt(ZonedDateTime.now());
        try {
          ObjectMapper mapper = new ObjectMapper()
              .registerModule(new JavaTimeModule())
              .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

          String json = mapper.writeValueAsString(notificationKafkaDto);

          kafkaTemplate.send(kafkaTopic, json);
          log.info("Sending carefully kafka from topic : {} payload : {} ", kafkaTopic,
              notificationKafkaDto.toString());
        } catch (Exception e) {
          throw new RuntimeException(
              "Failed to send notification to topic: "
                  + notificationKafkaDto.getNotificationTopic());
        }
      }

    };

  }


}
