package com.example.trainproject;

import com.example.trainproject.base.Config.MessageConfig;
import com.example.trainproject.base.Constant.Role;
import com.example.trainproject.base.Model.Card;
import com.example.trainproject.base.Model.KafkaProduceMessage;
import com.example.trainproject.base.Model.User;
import com.example.trainproject.base.Util.Wapper.TransferWrapper;
import com.example.trainproject.base.Service.KafkaProducerService;
import com.example.trainproject.base.Service.UserService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class TrainProjectApplication {


    private final PasswordEncoder passwordEncoder;

    private MessageConfig messageSource = new MessageConfig();
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
            System.out.println(kafkaProducedMessage);
        };
    }

    // @Bean
    public CommandLineRunner userCreation(
        UserService userService
    ) {
        return args -> {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRole(Role.ADMIN);
            userService.save(user);
        };
    }
    @Bean
    public CommandLineRunner wrapperClass(
        UserService userService
    ) {
        return args -> {
            Faker faker = new Faker();
            Card card = new Card();
            card.setCardNumber(faker.number().digits(10).toString());
            card.setFirstName(faker.name().firstName());
            card.setLastName(faker.name().lastName());
            card.setPin1(faker.number().digits(10).toString());
            card.setPin2(faker.number().digits(10).toString());
            TransferWrapper<Card> wrapper = new TransferWrapper<>(
                card,
                "user-service",
                "notification-service"
            );
        };
    }


}