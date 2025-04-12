package com.example.trainproject;

import com.example.trainproject.Model.KafkaProduceMessage;
import com.example.trainproject.service.KafkaProducerService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class TrainProjectApplication {

  public static void main(String[] args) {
    SpringApplication.run(TrainProjectApplication.class, args);

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
    };
  }

}
