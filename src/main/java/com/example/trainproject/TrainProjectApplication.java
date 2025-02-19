package com.example.trainproject;

import com.example.trainproject.Dto.OrderDto;
import com.example.trainproject.Repository.OrderRepository;
import com.example.trainproject.Repository.UserRepository;
import com.example.trainproject.Service.OrderService;
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

    @Bean
    public CommandLineRunner persist(
            UserRepository userRepositry,
            OrderRepository orderRepository,
            OrderService orderService
    ){
        return args -> {
//            User user = User.builder()
//                    .name("admin")
//                    .build();
//            userRepositry.save(user);
            OrderDto orderDto = new OrderDto();
            orderDto.setTotalAmount(5000);
            orderDto.setUserId(2L);
            orderService.persistOrder(orderDto);
        };
    }

}
