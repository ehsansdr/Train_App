package com.example.trainproject.repository;

import com.example.trainproject.Model.Task;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends ElasticsearchRepository<Task, String> {

}
