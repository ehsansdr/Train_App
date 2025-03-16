package com.example.trainproject;

import com.example.trainproject.base.Config.MessageConfig;
import com.example.trainproject.base.Dto.OrderDto;
import com.example.trainproject.base.Repository.OrderRepository;
import com.example.trainproject.base.Repository.UserRepository;
import com.example.trainproject.base.Service.OrderService;
import java.text.MessageFormat;
import java.util.Locale;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.MessageSource;
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

    //@Bean
    public CommandLineRunner persist(
        UserRepository userRepositry,
        OrderRepository orderRepository,
        OrderService orderService
    ) {
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
