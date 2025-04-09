package com.example.trainproject;

import com.example.trainproject.service.KafkaProducerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TrainProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainProjectApplication.class, args);

    }

}
