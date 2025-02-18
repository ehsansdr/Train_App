package com.example.trainproject.Service;

import com.example.trainproject.Dto.OrderDto;
import com.example.trainproject.Model.Order;
import com.example.trainproject.Repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
@AllArgsConstructor
@NoArgsConstructor
public class OrderService {


    private OrderRepository orderRepository;

    public Order createEntity() {
        Order order = new Order();  // Random number is set in the constructor
        // Check if the random number is unique before saving
        if (orderRepository.existsByshortNumber(order.getShortNumber())) {
            // If the random number already exists, generate a new one
            order.setShortNumber(generateRandomUniqueNumber());
        }
        return orderRepository.save(order);  // Save the order
    }

    private Integer generateRandomUniqueNumber() {
        Random random = new Random();
        Integer number = random.nextInt(1000000); // Random number between 0 and 999999
        return number;
    }
}
