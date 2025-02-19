package com.example.trainproject.Mapper;

import com.example.trainproject.Dto.OrderDto;
import com.example.trainproject.Exception.User.UserNotFoundException;
import com.example.trainproject.Model.Order;
import com.example.trainproject.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderMapper {

    private UserRepository userRepository;


    public Order returnOrder(OrderDto orderDto){
        Order order = Order.builder()
                .user(userRepository.findById(orderDto.getUserId())
                        .orElseThrow(() -> new UserNotFoundException("The user_id : " + orderDto.getUserId())))
                .totalAmount(orderDto.getTotalAmount())
                .build();
        return order;
    }
}
