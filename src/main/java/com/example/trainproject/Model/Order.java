package com.example.trainproject.Model;


import com.example.trainproject.Constant.OrderStatus;
import com.example.trainproject.Repository.OrderRepository;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

import java.time.ZonedDateTime;
import java.util.Random;

@Entity
@Slf4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_tbl")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_number")
    private  int shortNumber;

    @Column(name = "date")
    private ZonedDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_name")
    private User user;

    @Column(name = "total_amount")
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @PostPersist
    public void postPersist(){
        this.date = ZonedDateTime.now();
    }
}
