package com.example.trainproject.base.Repository;

import com.example.trainproject.base.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    boolean existsByshortNumber(int shortNumber);

}
