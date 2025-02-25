package com.example.trainproject.base.Mapper;

import com.example.trainproject.base.Dto.OrderDto;
import com.example.trainproject.base.Exception.User.UserNotFoundException;
import com.example.trainproject.base.Model.Order;
import com.example.trainproject.base.Repository.UserRepository;
import lombok.AllArgsConstructor;
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
