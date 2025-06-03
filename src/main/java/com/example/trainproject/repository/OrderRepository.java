package com.example.trainproject.repository;

import com.example.trainproject.Model.Order;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends ElasticsearchRepository<Order, String> {

}
