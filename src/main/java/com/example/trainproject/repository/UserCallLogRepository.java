package com.example.trainproject.repository;

import com.example.trainproject.Model.UserCallLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCallLogRepository extends ElasticsearchRepository<UserCallLog, String> {

}
