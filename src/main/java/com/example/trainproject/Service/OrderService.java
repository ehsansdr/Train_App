package com.example.trainproject.Service;

import com.example.trainproject.Dto.OrderDto;
import com.example.trainproject.Mapper.OrderMapper;
import com.example.trainproject.Model.Order;
import com.example.trainproject.Repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;


    public Order persistOrder(OrderDto orderDto){
        Order order = orderMapper.returnOrder(orderDto);
        return orderRepository.save(order);

    }


    public int createShortNameForOrder() {

        while (true) {
            if (!orderRepository.existsByshortNumber(generateRandomUniqueNumber())) {
                return generateRandomUniqueNumber();
            }else{
                continue;
            }
        }
    }

    public static Integer generateRandomUniqueNumber() {
        Random random = new Random();
        Integer number = random.nextInt(1000000); // Random number between 0 and 999999
        return number;
    }
}
