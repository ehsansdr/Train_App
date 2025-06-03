package com.example.trainproject.repository;

import com.example.trainproject.Model.CreditScore;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferralRepository extends ElasticsearchRepository<CreditScore, String> {

}
