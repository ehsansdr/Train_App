package com.example.trainproject.base.Model;


import com.example.trainproject.base.Constant.OrderStatus;
import com.example.trainproject.base.Service.OrderService;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Slf4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_tbl")
public class Order implements Serializable {
    // mean : factor

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_number")
    private  int shortNumber;

    @Column(name = "date")
    @CreationTimestamp
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
        this.orderStatus = OrderStatus.PENDING;
        this.shortNumber = OrderService.generateRandomUniqueNumber();
    }
}
