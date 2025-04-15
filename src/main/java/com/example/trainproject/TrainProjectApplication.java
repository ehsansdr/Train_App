package com.example.trainproject;

import com.example.trainproject.base.Config.MessageConfig;
import com.example.trainproject.base.Dto.OrderDto;
import com.example.trainproject.base.Model.KafkaProduceMessage;
import com.example.trainproject.base.Repository.OrderRepository;
import com.example.trainproject.base.Repository.UserRepository;
import com.example.trainproject.base.Service.KafkaProducerService;
import com.example.trainproject.base.Service.OrderService;
import com.github.javafaker.Faker;
import java.text.MessageFormat;
import java.util.Locale;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@SpringBootApplication
@EnableFeignClients
public class TrainProjectApplication {


    private MessageConfig messageSource = new MessageConfig();
    public static void main(String[] args) {
//        System.out.println(System.currentTimeMillis());
//        System.out.println(new BCryptPasswordEncoder().encode("P@ssw0rd"));

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
            System.out.println(kafkaProducedMessage);
        };
    }

}